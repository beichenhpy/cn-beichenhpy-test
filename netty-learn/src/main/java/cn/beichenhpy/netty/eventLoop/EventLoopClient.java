package cn.beichenhpy.netty.eventLoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventLoopClient description：
 * @since 2021/5/26 4:18 下午
 */
public class EventLoopClient {
    public static void main(String[] args) {
        try {
            Channel channel = new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new StringEncoder());
                                }
                            }
                    ).connect("127.0.0.1", 9999)
                    .sync()
                    .channel();
            System.out.println(channel);
            //这里debug时候 idea debug线程要选择Thread模式
            System.out.println("debug-pause");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
