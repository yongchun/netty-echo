package com.shannon.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.shannon.nio.bean.EchoRequest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Shannon,chen on 16/3/10.
 */

public class ServerMessageHandler extends ChannelHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerMessageHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        String body = new String(bytes, "UTF-8");
        LOGGER.info("The echo server receive request :" + body);

        EchoRequest echoRequest = JSON.parseObject(body, EchoRequest.class);

        int responseValue = echoRequest.getValue() * 100;
        echoRequest.setDesc("echo server response");
        echoRequest.setValue(responseValue);

        byte[] responseBytes = JSON.toJSONString(echoRequest).getBytes();
        ByteBuf resp = Unpooled.copiedBuffer(responseBytes);
        ctx.write(resp);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
