package cn.beichenhpy.netty.agreement.chat;

import cn.beichenhpy.netty.agreement.chat.protocol.MessageCodecShareable;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote ChatServer description：
 * @since 2021/6/1 3:32 下午
 */
@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecShareable messageCodec = new MessageCodecShareable();
        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline()
                                            .addLast(new LengthFieldBasedFrameDecoder(
                                                    1024,
                                                    12,
                                                    4,
                                                    0,
                                                    0
                                            ))
                                            .addLast(loggingHandler)
                                            .addLast(messageCodec);
                                }
                            }
                    ).bind(9999).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error:{}", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
