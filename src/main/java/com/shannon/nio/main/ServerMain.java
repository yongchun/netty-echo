package com.shannon.nio.main;

import com.shannon.nio.server.EchoServer;
import com.shannon.nio.server.ServerMessageHandler;

import io.netty.channel.ChannelHandlerAdapter;

/**
 * Created by Shannon,chen on 16/2/28.
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        ChannelHandlerAdapter handler = new ServerMessageHandler();
        EchoServer echoServer = new EchoServer();
        echoServer.bind("127.0.0.1", 8003, handler);
    }
}
