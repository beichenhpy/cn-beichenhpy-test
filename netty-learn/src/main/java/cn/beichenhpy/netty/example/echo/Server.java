package cn.beichenhpy.netty.example.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Server description：
 * @since 2021/5/27 4:33 下午
 */
@Slf4j
public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ChannelFuture future = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_RCVBUF, 5 * 1024)
                .childOption(ChannelOption.SO_SNDBUF, 5 * 1024)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(worker, new StringDecoder())
                                        .addLast(worker, new StringEncoder())
                                        .addLast(worker, new ChannelInboundHandlerAdapter() {
                                            @Override
                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                log.debug("echo server:{}", msg);
                                                ch.writeAndFlush(msg);
                                            }
                                        });
                            }
                        }
                ).bind(9999);
        //启动成功后执行此方法
        future.addListener(
                (ChannelFutureListener) future1 -> {
                    if (!future1.isSuccess()) {
                        log.error(future1.cause().getMessage());
                        future.channel().close();
                    }else {
                        log.debug("服务端启动成功");
                    }
                }
        );
        //关闭执行此方法
        future.channel().closeFuture().addListener(
                (ChannelFutureListener) future1 -> {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
        );
    }
}
