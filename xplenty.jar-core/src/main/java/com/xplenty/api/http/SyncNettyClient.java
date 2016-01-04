package com.xplenty.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.core.util.Base64;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.request.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.logging.InternalLogLevel;

import javax.net.ssl.SSLEngine;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 * Author: Xardas
 * Date: 23.12.15
 * Time: 19:11
 */
public class SyncNettyClient extends SimpleChannelUpstreamHandler implements HttpClient {
    public static final String MUTEX_KEY = "mutex";
    protected static final int MAX_CHANNEL_MEMORY_SIZE = 50 * 1024 * 1024; // 50 MB for channel
    protected static final long MAX_TOTAL_MEMORY_SIZE = 1 * 1024 * 1024 * 1024; // 1 G
    private final ClientBootstrap client;
    private final NioClientSocketChannelFactory chanfac;
    private final ThreadLocal<Channel> channel = new ThreadLocal<>();

    private final int timeout;
    private final String authHeader;
    private final String host;
    private final Http.Protocol protocol;
    private final String accountName;
    private final String apiKey;
    private final Xplenty.Version version;


    SyncNettyClient(String accountName, String apiKey, String host, Http.Protocol protocol, Xplenty.Version version, int timeout, boolean logHttpCommunication) throws XplentyAPIException {
        this.timeout = timeout;
        this.accountName = accountName;
        this.host = host;
        this.protocol = protocol;
        this.apiKey = apiKey;
        this.version = version;

        // generate basic auth header
        try {
            this.authHeader = String.format("Basic %s", new String(Base64.encode(String.format("%s:", apiKey)), "ASCII"));
        } catch (UnsupportedEncodingException e) {
            throw new XplentyAPIException("Error encoding API key", e);
        }

        chanfac = new NioClientSocketChannelFactory(
                new OrderedMemoryAwareThreadPoolExecutor(1, MAX_CHANNEL_MEMORY_SIZE, MAX_TOTAL_MEMORY_SIZE, 120, TimeUnit.SECONDS),
                new OrderedMemoryAwareThreadPoolExecutor(2, MAX_CHANNEL_MEMORY_SIZE, MAX_TOTAL_MEMORY_SIZE, 120, TimeUnit.SECONDS),
                2);
        client = new ClientBootstrap(chanfac);
        client.setOption("child.tcpNoDelay", true);
        client.setOption("child.keepAlive", true);
        client.setOption("reuseAddress", true);
        client.setOption("connectTimeoutMillis", timeout * 1000);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                client.releaseExternalResources();
            }
        }));
        client.setPipelineFactory(new PipelineFactory(this, protocol.equals(Http.Protocol.Https), logHttpCommunication));
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        HttpResponse response = (HttpResponse) e.getMessage();

        System.out.println(channelBuffer2String(response.getContent()));

        final Channel channel = ctx.getChannel();
        NettyResponse nettyResponse = (NettyResponse) channel.getAttachment();


        if (nettyResponse != null) {
            nettyResponse.setResponse(response);
            CountDownLatch mutex = nettyResponse.getMutex();
            mutex.countDown();
        }
    }

    public static String channelBuffer2String(ChannelBuffer channelBuffer) {
        return channelBuffer.toString(StandardCharsets.UTF_8);
    }

    private HttpMethod convertRequestMethod(Http.Method method) {
        switch (method) {
            case POST:
                return HttpMethod.POST;
            case PUT:
                return HttpMethod.PUT;
            case DELETE:
                return HttpMethod.DELETE;
            default:
                return HttpMethod.GET;
        }
    }


    @Override
    public <T> T execute(Request<T> xplentyRequest) throws XplentyAPIException {
        try {
            URL url = new URL(getMethodURL(xplentyRequest.getEndpoint(host, accountName)));
            Channel channel = getChannel(url);

            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, convertRequestMethod(xplentyRequest.getHttpMethod()), url.toString());
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaders.Names.HOST, url.getHost());
            headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP + ',' + HttpHeaders.Values.DEFLATE);
            headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaders.Names.USER_AGENT, "Xplenty Netty client by Xardazz");
            headers.set(HttpHeaders.Names.ACCEPT, getAcceptHeaderValue(xplentyRequest.getResponseType().value));
            headers.set(HttpHeaders.Names.AUTHORIZATION, authHeader);

            if (xplentyRequest.hasBody()) {
                final ChannelBuffer cb = ChannelBuffers.dynamicBuffer(8000);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectMapper objectMapper = JsonMapperFactory.getInstance();
                objectMapper.writeValue(bos, xplentyRequest.getBody());
                final byte[] contentBytes = bos.toByteArray();
                cb.writeBytes(contentBytes);
                request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, contentBytes.length);
                request.headers().set(HttpHeaders.Names.CONTENT_TYPE, Http.MediaType.JSON.value);
                request.setContent(cb);
            }

            NettyResponse response = new NettyResponse();
            channel.setAttachment(response);
            channel.write(request).awaitUninterruptibly();
            response.getMutex().await(timeout, TimeUnit.SECONDS);

            final HttpResponse httpBody = response.getResponse();
            if (httpBody == null) {
                throw new XplentyAPIException("Request timed out!");
            }
            int httpStatus = httpBody.getStatus().getCode();
            Response processedResponse = Response.forContentType(xplentyRequest.getResponseType(), channelBuffer2String(httpBody.getContent()), httpStatus, convertNettyHeaders(httpBody.headers()));
            processedResponse.validate(xplentyRequest.getName());
            return xplentyRequest.getResponse(processedResponse);
        } catch (Exception e) {
            throw new XplentyAPIException(e);
        }
    }

    private Channel getChannel(URL url) throws XplentyAPIException {
        Channel chan = channel.get();
        // if channel is valid no need to reconnect
        if (chan == null || !chan.isConnected() || !chan.isOpen()) {
            try {
                final ChannelFuture channelFuture = client.connect(new InetSocketAddress(url.getHost(), getPort(url)));
                channelFuture.await(timeout, TimeUnit.SECONDS);
                if (channelFuture.isDone() && channelFuture.isSuccess()) {
                    chan = channelFuture.getChannel();
                    channel.set(chan);
                } else {
                    throw new XplentyAPIException("Error connecting to Xplenty server:", channelFuture.getCause());
                }
            } catch (InterruptedException ex) {
                throw new XplentyAPIException("Interrupted while getting channel!", ex);
            }
        }
        return chan;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent cause) throws Exception {
        ctx.getChannel().close();
        throw new XplentyAPIException("Exception while communicating with remote Xplenty server", cause.getCause());
    }

    private String getAcceptHeaderValue(String baseValue) {
        if (this.version == null) {
            return baseValue;
        }
        return String.format("%s; %s", baseValue, this.version.format());
    }

    private Map<String, String> convertNettyHeaders(HttpHeaders headers) {
        final Map<String, String> convertedHeaders = new HashMap<>();
        for (Map.Entry<String, String> header : headers.entries()) {
            convertedHeaders.put(header.getKey(), header.getValue());
        }
        return convertedHeaders;
    }

    /**
     * Constructs the actual URL
     * @param methodEndpoint - describes the action type
     * @return filly qualified URL
     */
    private String getMethodURL(String methodEndpoint) {
        return String.format("%s://%s", protocol, methodEndpoint);
    }


    private int getPort(URL url) {
        if (url.getPort() > 0) {
            return url.getPort();
        }
        if (url.getProtocol().equals("https")) {
            return DEFAULT_HTTPS_PORT;
        }
        return DEFAULT_HTTP_PORT;
    }


    @Override
    public void shutdown() {
        //client.shutdown();    // does the same
        client.releaseExternalResources();
    }


    private static class PipelineFactory implements ChannelPipelineFactory {

        private final boolean ssl;
        private final boolean logCommunication;
        private final ChannelUpstreamHandler handler;

        public PipelineFactory(ChannelUpstreamHandler handler, boolean ssl, boolean logCommunication) {
            this.handler = handler;
            this.ssl = ssl;
            this.logCommunication = logCommunication;
        }

        public ChannelPipeline getPipeline() throws Exception {
            // Create a default pipeline implementation.
            ChannelPipeline pipeline = pipeline();

            // Enable HTTPS if necessary.
            if (ssl) {
                // trust to any server certificate
                // example implementation with comparison to keystore
                // http://www.programcreek.com/java-api-examples/index.php?source_dir=search-guard-master/src/main/java/com/floragunn/searchguard/transport/SSLNettyTransport.java
                SSLEngine engine = SSLEngineDefaultImpl.getSSLContext().createSSLEngine();
                engine.setUseClientMode(true);

                pipeline.addLast("ssl", new SslHandler(engine));
            }

            if (logCommunication) {
                pipeline.addLast("logger", new LoggingHandler("nettyclient", InternalLogLevel.DEBUG, true));
            }

            pipeline.addLast("codec", new HttpClientCodec());

            pipeline.addLast("aggregator", new HttpChunkAggregator(102467890));

            // Remove the following line if you don't want automatic content decompression.
            pipeline.addLast("deflater", new HttpContentDecompressor());

            pipeline.addLast("handler", handler);
            return pipeline;
        }
    }



    private static class NettyResponse {
        private final CountDownLatch mutex = new CountDownLatch(1);
        private volatile HttpResponse response;

        public CountDownLatch getMutex() {
            return mutex;
        }

        public HttpResponse getResponse() {
            return response;
        }

        public void setResponse(HttpResponse response) {
            this.response = response;
        }
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Http.Protocol getProtocol() {
        return protocol;
    }

    @Override
    public Xplenty.Version getVersion() {
        return version;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }
}
