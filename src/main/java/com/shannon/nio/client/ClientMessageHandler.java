package com.shannon.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Shannon,chen on 16/3/10.
 */
public class ClientMessageHandler extends ChannelHandlerAdapter {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientMessageHandler.class.getName());
    private ByteBuf req;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = new String(req, "UTF-8");

        LOGGER.info("the echo cli  receive response from server is :" + body);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(req);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    public ByteBuf getReq() {
        return req;
    }

    public void setReq(ByteBuf req) {
        this.req = req;
    }
}
