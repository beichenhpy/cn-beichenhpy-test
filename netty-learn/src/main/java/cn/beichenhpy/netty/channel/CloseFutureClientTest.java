package cn.beichenhpy.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote CloseFutureClientTest description：希望用户不断输入，直到q退出
 * <br> 注意：记得关闭所有的EventLoopGroup连接 {@link EventLoopGroup#shutdownGracefully()}
 * <br>推荐使用addListener代替sync,是异步非阻塞执行任务的
 * @since 2021/5/26 9:25 下午
 */
@Slf4j
public class CloseFutureClientTest {
    public void clientStart() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new StringEncoder());
                                //netty自带日志
                                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            }
                        }
                ).connect(new InetSocketAddress("localhost", 9999));
        //异步非阻塞代替sync()，还可以避免try catch
        channelFuture.addListener(
                (ChannelFutureListener) future -> {
                    Channel channel = future.channel();
                    //启动新线程处理scanner
                    new Thread(
                            () -> {
                                Scanner scanner = new Scanner(System.in);
                                while (true) {
                                    String s = scanner.nextLine();
                                    if (Objects.equals(s, "q")) {
                                        //close也是异步非阻塞的
                                        channel.close();
                                        break;
                                    }
                                    channel.writeAndFlush(s);
                                }
                            }
                    ).start();
                    //获取ClosedFuture对象 两种方式 1、同步 2、异步
                    ChannelFuture closeFuture = channel.closeFuture();
                    /*log.debug("等待关闭。。。。");
                    //1.同步
                    closeFuture.sync();
                    log.debug("关闭后的channel:{}",channel);
                    group.shutdownGracefully();*/
                    //2.异步listener
                    closeFuture.addListener(
                            (ChannelFutureListener) future1 -> {
                                log.debug("关闭后的channel:{}", channel);
                                //优雅关闭所有的nio线程
                                group.shutdownGracefully();
                            }
                    );
                });
    }

    public static void main(String[] args) {
        new CloseFutureClientTest().clientStart();
    }
}
