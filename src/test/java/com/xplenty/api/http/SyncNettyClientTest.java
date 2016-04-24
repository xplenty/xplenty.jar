package com.xplenty.api.http;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import junit.framework.TestCase;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 8:52
 */
public class SyncNettyClientTest extends TestCase {

    public void testBuilder() {
        SyncNettyClient client = new SyncNettyClient("a", "b", "c", Http.Protocol.Https, Xplenty.Version.V2, 10, true);
        assertEquals("a", client.getAccountName());
        assertEquals("b", client.getApiKey());
        assertEquals("c", client.getHost());
        assertEquals(Http.Protocol.Https, client.getProtocol());
        assertEquals(Xplenty.Version.V2, client.getVersion());
        assertEquals(10, client.getTimeout());
    }

    public void testMessageRecieved() throws Exception {
        final DefaultChannelPipeline pipeline = new DefaultChannelPipeline();
        final Channel chan = new AbstractChannel(null, null, pipeline, new ChannelSink() {


            @Override
            public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {

            }

            @Override
            public void exceptionCaught(ChannelPipeline pipeline, ChannelEvent e, ChannelPipelineException cause) throws Exception {

            }

            @Override
            public ChannelFuture execute(ChannelPipeline pipeline, Runnable task) {
                return null;
            }
        }) {

            @Override
            public ChannelConfig getConfig() {
                return null;
            }

            @Override
            public boolean isBound() {
                return false;
            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public SocketAddress getLocalAddress() {
                return new InetSocketAddress("localhost", 888);
            }

            @Override
            public SocketAddress getRemoteAddress() {
                return new InetSocketAddress("localhost", 666);
            }
        };

        ChannelHandlerContext ctx = new ChannelHandlerContext() {
            final SyncNettyClient.NettyResponse nettyResponse = new SyncNettyClient.NettyResponse();

            @Override
            public Channel getChannel() {
                chan.setAttachment(nettyResponse);
                return chan;
            }

            @Override
            public ChannelPipeline getPipeline() {
                return pipeline;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public ChannelHandler getHandler() {
                return null;
            }

            @Override
            public boolean canHandleUpstream() {
                return false;
            }

            @Override
            public boolean canHandleDownstream() {
                return false;
            }

            @Override
            public void sendUpstream(ChannelEvent e) {

            }

            @Override
            public void sendDownstream(ChannelEvent e) {

            }

            @Override
            public Object getAttachment() {

                return nettyResponse;
            }

            @Override
            public void setAttachment(Object attachment) {

            }
        };

        SyncNettyClient client = new SyncNettyClient("a", "b", "c", Http.Protocol.Https, Xplenty.Version.V2, 10, true);

        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        final ChannelBuffer cb = ChannelBuffers.dynamicBuffer(8000);
        cb.writeBytes("{\"a\" : 9}".getBytes());
        cb.resetReaderIndex();
        response.setContent(cb);
        MessageEvent me = new DownstreamMessageEvent(chan, new DefaultChannelFuture(chan, true), response, null);
        client.messageReceived(ctx, me);

        SyncNettyClient.NettyResponse processedResp = (SyncNettyClient.NettyResponse) ctx.getAttachment();
        assertEquals(200, processedResp.getResponse().getStatus().getCode());
        assertEquals("{\"a\" : 9}", client.channelBuffer2String(processedResp.getResponse().getContent()));

        try {
            client.exceptionCaught(ctx, new DefaultExceptionEvent(chan, new Exception("some exception")));
        } catch (Exception ex) {
            assertEquals(XplentyAPIException.class, ex.getClass());
            assertEquals("Exception while communicating with remote Xplenty server", ex.getMessage());
        }

        client.shutdown();

    }

    public void testInnerMethods() throws Exception {
        SyncNettyClient client = new SyncNettyClient("a", "b", "c", Http.Protocol.Https, Xplenty.Version.V2, 10, true);

        assertEquals("ogg; version=2", client.getAcceptHeaderValue("ogg"));
        assertEquals("https://a/b", client.getMethodURL("a/b"));
        assertEquals("http://a/b", client.getMethodURL("http://a/b"));
        assertEquals(SyncNettyClient.DEFAULT_HTTP_PORT, client.getPort(new URL("http://localhost/")));
        assertEquals(SyncNettyClient.DEFAULT_HTTPS_PORT, client.getPort(new URL("https://localhost/")));
        assertEquals(867, client.getPort(new URL("http://localhost:867/")));

        assertEquals(HttpMethod.POST, client.convertRequestMethod(Http.Method.POST));
        assertEquals(HttpMethod.PUT, client.convertRequestMethod(Http.Method.PUT));
        assertEquals(HttpMethod.GET, client.convertRequestMethod(Http.Method.GET));
        assertEquals(HttpMethod.DELETE, client.convertRequestMethod(Http.Method.DELETE));

        HttpHeaders nettyHeaders = new DefaultHttpHeaders();
        nettyHeaders.add(HttpHeaders.Names.ACCEPT_CHARSET, "utf8");
        nettyHeaders.add(HttpHeaders.Names.CONTENT_TYPE, "json");
        Map<String, String> headers = client.convertNettyHeaders(nettyHeaders);
        assertNotNull(headers);
        assertEquals(2, headers.size());
        assertEquals("utf8", headers.get(HttpHeaders.Names.ACCEPT_CHARSET));
        assertEquals("json", headers.get(HttpHeaders.Names.CONTENT_TYPE));
    }
}
