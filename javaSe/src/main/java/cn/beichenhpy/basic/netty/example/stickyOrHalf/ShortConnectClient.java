package cn.beichenhpy.basic.netty.example.stickyOrHalf;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Client description：短连接方式解决粘包
 * @since 2021/6/1 10:37 上午
 */
@Slf4j
public class ShortConnectClient {
    void start(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture future = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG))
                                            .addLast(new ChannelInboundHandlerAdapter(){
                                                //连接服务器能够触发active事件
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    ByteBuf buffer = ctx.alloc().buffer(16);
                                                    buffer.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
                                                    ctx.writeAndFlush(buffer);
                                                    ctx.channel().close();
                                                }
                                            });
                                }
                            }
                    ).connect(new InetSocketAddress("localhost", 9999)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new ShortConnectClient().start();
        }
    }
}
