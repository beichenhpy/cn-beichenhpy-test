package cn.beichenhpy.basic.netty.hello_world;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Server description：netty server端
 * @since 2021/5/26 2:45 下午
 */
public class Server {
    public static void main(String[] args) {
        //1.启动器
        new ServerBootstrap()
                /*
                 * 2.事件组(boss/worker) eventLoop 接收事件处理
                 * 【accept】->调用初始化方法 ChannelInitializer#initChannel
                 * 【read】->调用具体的handler(ex StringDecoder)
                 * 第一个参数 parentEventGroup 处理 accept 事件  第二个参数 childEventGroup 处理 read/write事件
                 */
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                //3.服务器channel
                .channel(NioServerSocketChannel.class)
                //4.具体worker(child)实现 Handler
                .childHandler(
                        //5.代表和客户端读写的通道
                        new ChannelInitializer<NioSocketChannel>() {
                            //6.初始化Channel  连接建立后才会执行 accept方法调用的此方法
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                //将ByteBuf -> String
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new StringEncoder());
                                //自定义handler
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    //打印读字符串
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                        ctx.channel().writeAndFlush("收到了");
                                    }
                                });
                            }
                        })
                .bind(9999);
    }
}
