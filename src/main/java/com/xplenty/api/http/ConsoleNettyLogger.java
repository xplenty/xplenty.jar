package com.xplenty.api.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpMessage;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 20:24
 */
public class ConsoleNettyLogger implements ChannelUpstreamHandler, ChannelDownstreamHandler {

    private ThreadLocal<DateFormat> df = new ThreadLocal<>();
    private final boolean logHttpBodyOnly;

    public ConsoleNettyLogger(boolean logHttpBodyOnly) {
        this.logHttpBodyOnly = logHttpBodyOnly;
    }


    @Override
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent channelEvent) throws Exception {
        log(channelEvent, false);
        if (ctx != null) {
            ctx.sendDownstream(channelEvent);
        }
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent channelEvent) throws Exception {
        log(channelEvent, true);
        if (ctx != null) {
            ctx.sendUpstream(channelEvent);
        }
    }

    protected void log(ChannelEvent event, boolean isIncoming) {
        if (event instanceof MessageEvent) {
            MessageEvent me = (MessageEvent) event;
            if (me.getMessage() instanceof ChannelBuffer) {
                logMessage((ChannelBuffer) me.getMessage(), isIncoming);
            } else if (HttpMessage.class.isAssignableFrom(me.getMessage().getClass()) && logHttpBodyOnly) {
                logHttpMessage(((HttpMessage) me.getMessage()).getContent(), true);
            }
        } else if (event instanceof ExceptionEvent && !logHttpBodyOnly) {
            logException(((ExceptionEvent) event).getCause());
        } else if (!logHttpBodyOnly) {
            logStateMessage(event.toString());
        }
    }

    protected void logStateMessage(String str) {
        System.err.println(String.format("%s %s", getDateFormat().format(System.currentTimeMillis()), str));
    }

    protected void logMessage(ChannelBuffer buf, boolean isIncoming) {
        System.err.println(String.format("%s %s", getDateFormat().format(System.currentTimeMillis()), isIncoming ? "<<<" : ">>>"));
        System.err.println(buf.toString(StandardCharsets.UTF_8));
        System.err.println(isIncoming ? "<<<" : ">>>");
    }

    protected void logHttpMessage(ChannelBuffer buf, boolean isIncoming) {
        System.err.println(String.format("%s [DECODED]%s", getDateFormat().format(System.currentTimeMillis()), isIncoming ? "<<<" : ">>>"));
        System.err.println(buf.toString(StandardCharsets.UTF_8));
        System.err.println(isIncoming ? "[DECODED]<<<" : "[DECODED]>>>");
    }

    protected void logException(Throwable ex) {
        System.err.println(ex.getMessage());
    }

    private DateFormat getDateFormat() {
        DateFormat dateFormat = df.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS");
            df.set(dateFormat);
        }
        return dateFormat;
    }
}
