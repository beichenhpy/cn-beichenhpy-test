package cn.beichenhpy.basic.netty.agreement;

import cn.beichenhpy.basic.netty.byteBuf.ByteBufTest;
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
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestRedis description：协议尝试，通过客户端连接Redis
 * Redis的协议：
 * ex: set name zhangsan
 * 每帧后都要有\n\t
 * 先确定所有的命令数组长度
 * *3\n\t
 * 确定每个数组元素的长度
 * $3\n\t
 * set\n\t
 * $4\n\t
 * name\n\t
 * $8\n\t
 * zhangsan\n\t
 * @since 2021/6/1 1:03 下午
 */
@Slf4j
public class TestRedis {
    void start(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
           final byte[] LINE = {13,10};
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
                                                    //零拷贝 省内存
                                                    ByteBuf copy = buffer.duplicate();
                                                    //防止buffer被release影响copy
                                                    copy.retain();
                                                    //先认证 auth beichenhpy
                                                    buffer.writeBytes("*2".getBytes(StandardCharsets.UTF_8));
                                                    buffer.writeBytes(LINE);
                                                    buffer.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                                                    buffer.writeBytes(LINE);
                                                    buffer.writeBytes("auth".getBytes(StandardCharsets.UTF_8));
                                                    buffer.writeBytes(LINE);
                                                    buffer.writeBytes("$10".getBytes(StandardCharsets.UTF_8));
                                                    buffer.writeBytes(LINE);
                                                    buffer.writeBytes("beichenhpy".getBytes(StandardCharsets.UTF_8));
                                                    buffer.writeBytes(LINE);
                                                    ctx.writeAndFlush(buffer);
                                                    //set name zhangsan
                                                    copy.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("$8".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    copy.writeBytes("zhangsan".getBytes(StandardCharsets.UTF_8));
                                                    copy.writeBytes(LINE);
                                                    ctx.writeAndFlush(copy);
                                                }

                                                @Override
                                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                    ByteBuf buf = (ByteBuf) msg;
                                                    log.debug("接收消息：{}",buf.toString());
                                                }
                                            });
                                }
                            }
                    ).connect(new InetSocketAddress("localhost", 6379)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TestRedis().start();
    }
}
