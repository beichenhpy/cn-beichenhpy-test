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
import java.util.Random;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote LineBasedDecodeClient description：根据换行进行切割解决粘包半包问题测试客户端
 * @since 2021/6/1 12:00 下午
 */
@Slf4j
public class LineBasedDecodeClient {

    /**
     * 通过字符 拼接一个最后为 \n 的字符串
     * @param c 字符
     * @param len 长度
     * @return 返回字符串
     */
    public static String getStrings(char c,int len){
        StringBuilder sb = new StringBuilder(len + 2);
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb.toString();
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
                                                    char c = '0';
                                                    Random r = new Random();
                                                    for (int i = 0; i < 10; i++) {
                                                        buffer.writeCharSequence(getStrings(c++,r.nextInt(256) + 1), Charset.defaultCharset());
                                                    }
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
        new LineBasedDecodeClient().start();
    }

}
