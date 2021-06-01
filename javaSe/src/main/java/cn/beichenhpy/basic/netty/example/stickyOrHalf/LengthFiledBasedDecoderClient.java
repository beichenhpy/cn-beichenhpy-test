package cn.beichenhpy.basic.netty.example.stickyOrHalf;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote LengthFiledBasedDecoderClient description：
 * 通过定义协议帧，添加长度帧来进行分割
 * @since 2021/6/1 12:39 下午
 */
@Slf4j
public class LengthFiledBasedDecoderClient {
    /**
     * 4个长度帧  + 一个版本帧 + 数据帧
     * @param buf buff
     * @param version 版本
     * @param content 数据帧内容
     */
    public void ready(ByteBuf buf,int version,String content){
        buf.writeInt(content.length());
        buf.writeByte(version);
        buf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
    }
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
                                    ch.pipeline()
                                            .addLast(new LoggingHandler(LogLevel.DEBUG))
                                            .addLast(new ChannelInboundHandlerAdapter(){
                                                //连接服务器能够触发active事件
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    ByteBuf buffer = ctx.alloc().buffer();
                                                    //定义协议   4个长度帧  + 一个版本帧 + 数据帧
                                                    ready(buffer,0,"hello server!");
                                                    ready(buffer,1,"get it!");
                                                    ctx.writeAndFlush(buffer);
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
        new LengthFiledBasedDecoderClient().start();
    }

}
