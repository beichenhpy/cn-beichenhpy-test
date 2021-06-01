package cn.beichenhpy.netty.hello_world;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Client description：
 * @since 2021/5/26 2:45 下午
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        //1.启动类
        new Bootstrap()
                /*
                 * 2.事件组(boss/worker) eventLoop 接收事件处理
                 * 【accept】->调用初始化方法 ChannelInitializer#initChannel
                 * 【read】->调用具体的handler(ex StringDecoder)
                 */
                .group(new NioEventLoopGroup())
                //3.添加客户端channel
                .channel(NioSocketChannel.class)
                //4.添加处理器
                .handler(
                        new ChannelInitializer<NioSocketChannel>() {

                            //初始化NioSocketChannel 连接建立后才会执行
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                //添加String编码器 将 String -> ByteBuf
                                ch.pipeline().addLast(new StringEncoder());
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                    }
                                });
                            }
                        }
                )
                //连接 触发服务端accept事件
                .connect("127.0.0.1", 9999)
                //同步阻塞方法，直到连接建立后才执行 sync方法
                .sync()
                //获取连接的Channel
                .channel()
                //向服务器发送数据-->会进入具体的handler中（ex StringEncoder)
                .writeAndFlush("hello_world");
    }
}
