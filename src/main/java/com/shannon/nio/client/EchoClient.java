package com.shannon.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Shannon,chen on 16/2/28.
 * <p/>
 * 客户端
 */
public class EchoClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(EchoClient.class.getName());

    public void connect(String host, int port, final ChannelHandlerAdapter messageHandler) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(messageHandler);

                        }
                    });
            ChannelFuture channelFuture = bs.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }

    }

}
