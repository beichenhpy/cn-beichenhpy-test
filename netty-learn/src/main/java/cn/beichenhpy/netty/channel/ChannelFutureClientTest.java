package cn.beichenhpy.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventLoopClient description：ChannelFuture
 * <br> 带有Future、Promise的都是配合异步方法使用的，用来处理结果
 * @since 2021/5/26 4:18 下午
 */
@Slf4j
public class ChannelFutureClientTest {
    public static void main(String[] args) {
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new StringEncoder());
                                }
                            }
                    )
                    /*
                    异步非阻塞方法-> main发起调用，真正执行的线程是nio线程
                    连接比较慢，假设需要1s
                    那么假设无sync(),那么直接无阻塞获取channel(),但是这时channel是未建立连接的channel，所以无法发送
                     */
                    .connect("127.0.0.1", 9999);
//           /*
//            处理Future结果
//            方法1：使用sync()方法同步处理结果 阻塞住当前线程，直到nio线程连接建立完毕
//            */
//            channelFuture.sync();
//            Channel channel = channelFuture.channel();
//            /*
//              -- 使用sync() - [id: 0xf9761a9a, L:/127.0.0.1:55235 - R:/127.0.0.1:9999] ----main线程
//              -- 不使用sync() -  [id: 0xf9761a9a] ---- main线程
//              说明未建立连接
//             */
//            log.debug("channel信息：{}", channel);
//            channel.writeAndFlush("channelFuture");

            /*
              处理Future结果
              方法2：使用addListener处理结果 等待结果也不是main线程
             */
            //在nio线程连接建立好后，回调此方法
            channelFuture.addListener(
                    (ChannelFutureListener) future -> {
                        Channel channel = future.channel();
                        /*
                          [id: 0xf9761a9a, L:/127.0.0.1:55235 - R:/127.0.0.1:9999] ---- nio线程
                         */
                        log.debug("channel信息：{}",channel);
                        channel.writeAndFlush("addListener");
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
