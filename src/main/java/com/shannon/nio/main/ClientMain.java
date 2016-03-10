package com.shannon.nio.main;

import com.alibaba.fastjson.JSON;
import com.shannon.nio.bean.EchoRequest;
import com.shannon.nio.client.EchoClient;
import com.shannon.nio.client.ClientMessageHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by Shannon,chen on 16/2/28.
 */
public class ClientMain {
    public static void main(String[] args) {
        ClientMessageHandler handler = new ClientMessageHandler();

        EchoRequest request = new EchoRequest();
        request.setDesc("echo client request");
        request.setValue(12);
        String content = JSON.toJSONString(request);
        ByteBuf req = Unpooled.buffer(content.getBytes().length);
        req.writeBytes(content.getBytes());
        handler.setReq(req);

        EchoClient echoClient = new EchoClient();

        echoClient.connect("127.0.0.1", 8003, handler);
    }
}
