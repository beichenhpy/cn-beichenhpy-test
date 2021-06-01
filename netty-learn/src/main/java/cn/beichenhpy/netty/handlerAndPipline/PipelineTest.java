package cn.beichenhpy.netty.handlerAndPipline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PipelineTest description：pipeline相关学习
 * <br> {@link ChannelInboundHandlerAdapter#channelRead(ChannelHandlerContext, Object)} 这个方法用来向后切换handler 入栈handler
 * <br> {@link ChannelOutboundHandlerAdapter#write(ChannelHandlerContext, Object, ChannelPromise)} 这个方法向前切换 handler 出栈handler
 * <br> 注意：1、{@link ChannelHandlerContext#writeAndFlush(Object)} 这个方法，只会从当前的handler开始向前寻找【出栈】的handler
 * <br> 2.{@link Channel#writeAndFlush(Object)} 这个方法则会从tail开始寻找所有的【出栈】handler
 * @since 2021/5/27 12:55 下午
 */
@Slf4j
public class PipelineTest {
    public static void main(String[] args) {
        NioEventLoopGroup accept = new NioEventLoopGroup(1);
        NioEventLoopGroup other = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(accept,other)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                // 添加handler 内部是双向链表 head -> h1 -> h2 -> h3 -> h4 -> h5 -> h6 -> tail
                                //入栈是从 head开始读取执行
                                //出站是从 tail开始读取执行
                                pipeline.addLast(other,"h1",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("1.将obj转为String");
                                        //转为String传递给下一个handler
                                        ByteBuf buf = (ByteBuf) msg;
                                        String name = buf.toString(Charset.defaultCharset());
                                        //这个方法相当于将结果传递到下一个handler
                                        super.channelRead(ctx, name);
                                    }
                                });
                                pipeline.addLast(other,"h2",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("2.封装到Student中");
                                        Student student = new Student(msg.toString());
                                        super.channelRead(ctx, student);
                                    }
                                });
                                pipeline.addLast(other,"h3",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("3.打印student:{}",msg);
                                        //出栈需要写触发
                                        ch.writeAndFlush(ctx.alloc().buffer().writeBytes("attack".getBytes()));
                                    }
                                });

                                pipeline.addLast(other,"h4",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("4");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast(other,"h5",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("5");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast(other,"h6",new ChannelOutboundHandlerAdapter(){
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("6");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                            }
                        }
                )
                .bind(9999);
    }
    @Data
    @AllArgsConstructor
    static class Student{
        private String name;
    }
}
