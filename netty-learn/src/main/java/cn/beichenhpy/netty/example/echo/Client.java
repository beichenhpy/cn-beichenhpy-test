package cn.beichenhpy.netty.example.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Client description：
 * @since 2021/5/27 4:45 下午
 */
@Slf4j
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup event = new NioEventLoopGroup();
        ChannelFuture future = new Bootstrap()
                .group(event)
                .channel(NioSocketChannel.class)
                .handler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new StringEncoder())
                                        .addLast(new StringDecoder())
                                        .addLast(new ChannelInboundHandlerAdapter() {
                                            @Override
                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                log.debug("echo-client:{}", msg);
                                                ch.writeAndFlush(msg);
                                            }
                                        });
                            }
                        }
                ).connect(new InetSocketAddress("localhost", 9999));
        //连接成功后执行的方法
        future.addListener((ChannelFutureListener) future1 -> {
            log.debug("连接成功。。。");
            future1.channel().writeAndFlush("test");
        });
        //如果自动关闭连接后执行的方法
        future.channel().closeFuture().addListener((ChannelFutureListener) future12 -> event.shutdownGracefully());
    }
}
