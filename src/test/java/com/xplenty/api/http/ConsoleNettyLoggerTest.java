package com.xplenty.api.http;

import junit.framework.TestCase;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;


/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 7:42
 */
public class ConsoleNettyLoggerTest extends TestCase {
    public void testLogger() throws Exception {

        String lineSeparator = System.getProperty("line.separator");
        ConsoleNettyLogger cne = new ConsoleNettyLogger(false);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream myps = new PrintStream(bos);
        System.setErr(myps);
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
        ChannelStateEvent cse = new ChannelStateEvent() {
            @Override
            public ChannelState getState() {
                return ChannelState.OPEN;
            }

            @Override
            public Object getValue() {
                return "[id 666]";
            }

            @Override
            public Channel getChannel() {
                return chan;
            }

            @Override
            public ChannelFuture getFuture() {
                return new DefaultChannelFuture(chan, true);
            }

            @Override
            public String toString() {
                return String.format("[id: 0x%s]: state %s", Integer.toHexString(chan.getId()), getState());
            }
        };
        cne.handleDownstream(pipeline.getContext(cne), cse);
        String res = bos.toString();
        assertTrue(res.endsWith(String.format("[id: 0x%s]: state OPEN%s", Integer.toHexString(chan.getId()), lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.handleUpstream(pipeline.getContext(cne), cse);
        res = bos.toString();
        assertTrue(res.endsWith(String.format("[id: 0x%s]: state OPEN%s", Integer.toHexString(chan.getId()), lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.log(cse, true);
        res = bos.toString();
        assertTrue(res.endsWith(String.format("[id: 0x%s]: state OPEN%s", Integer.toHexString(chan.getId()), lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logStateMessage("some important log msg");
        res = bos.toString();
        assertTrue(res.endsWith("some important log msg" + lineSeparator));

        final ChannelBuffer cb = ChannelBuffers.dynamicBuffer(8000);
        cb.writeBytes("some important log msg".getBytes());
        cb.resetReaderIndex();

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logMessage(cb, false);
        res = bos.toString();
        assertTrue(res.endsWith(String.format(">>>%ssome important log msg%s>>>%s", lineSeparator, lineSeparator, lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logMessage(cb, true);
        res = bos.toString();
        assertTrue(res.endsWith(String.format("<<<%ssome important log msg%s<<<%s", lineSeparator, lineSeparator, lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logHttpMessage(cb, true);
        res = bos.toString();
        assertTrue(res.endsWith(String.format("<<<%ssome important log msg%s[DECODED]<<<%s", lineSeparator, lineSeparator, lineSeparator)));

        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logHttpMessage(cb, false);
        res = bos.toString();
        assertTrue(res.endsWith(String.format(">>>%ssome important log msg%s[DECODED]>>>%s", lineSeparator, lineSeparator, lineSeparator)));


        bos = new ByteArrayOutputStream();
        myps = new PrintStream(bos);
        System.setErr(myps);
        cne.logException(new Exception("bad things"));
        res = bos.toString();
        assertTrue(res.endsWith(String.format("bad things%s", lineSeparator)));
    }
}
