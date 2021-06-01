package cn.beichenhpy.netty.agreement;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestHttp description：测试Netty提供的http编解码处理器
 * @see io.netty.handler.codec.http.HttpServerCodec
 * @since 2021/6/1 1:55 下午
 */
@Slf4j
public class TestHttp {
    void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline()
                                            .addLast(new LoggingHandler(LogLevel.DEBUG))
                                            /**
                                             * http编解码组合handler 会将http请求处理为两个handler 1.HttpRequest 2.HttpContent
                                             * @see HttpRequest
                                             * @see HttpContent
                                             */
                                            .addLast(new HttpServerCodec())
                                            //使用这个Handler可以利用泛型来处理对应的参数
                                            .addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                                @Override
                                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                                    log.debug("当前的uri:{}", msg.uri());
                                                    //设置返回response
                                                    DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(
                                                            msg.protocolVersion(),HttpResponseStatus.OK
                                                    );
                                                    byte[] bytes = "<h1>hello_world</h1>".getBytes(StandardCharsets.UTF_8);
                                                    defaultHttpResponse.content().writeBytes(bytes);
                                                    //设置content-length 不然浏览器会一直请求
                                                    defaultHttpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,bytes.length);
                                                    ctx.writeAndFlush(defaultHttpResponse);
                                                }
                                            })
                                            .addLast(new SimpleChannelInboundHandler<HttpContent>() {
                                                @Override
                                                protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
                                                    log.debug("{}",msg.content().toString());
                                                }
                                            });
                                }
                            }
                    ).bind(9999).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TestHttp().start();
    }
}
