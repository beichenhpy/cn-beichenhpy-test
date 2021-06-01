package cn.beichenhpy.basic.netty.example.stickyOrHalf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Server description：粘包服务端模拟
 * @since 2021/6/1 10:32 上午
 */
@Slf4j
public class Server {
    void start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    //修改netty的最大接收值
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16))
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline()
                                            /**
                                             * 定长消息解码器 会造成空间浪费-对应测试Client
                                             * @see FileLengthDecoderClient
                                             */
//                                            .addLast(new FixedLengthFrameDecoder(10))
                                            /**
                                             * maxLength指最大长度没遇到换行符
                                             * @see LineBasedDecodeClient
                                             */
//                                            .addLast(new LineBasedFrameDecoder(1024))
                                            /**
                                             * maxFrameLength 最大帧长度 1024
                                             * lengthFiledOffset 定义长度的帧从哪帧开始 设置为0 从第一帧开始
                                             * lengthFiledLength 定义长度的帧有有多少位 写入为Int 有4位
                                             * lengthAdjustment 定义长度帧后多少位是数据帧？ 中间插入了个版本帧 所以版本帧后1位是数据帧
                                             * initialBytesToStrip 需要从哪位开始截断？
                                             * @see LengthFiledBasedDecoderClient
                                             */
                                            .addLast(new LengthFieldBasedFrameDecoder(
                                                    1024,
                                                    0,
                                                    4,
                                                    1,
                                                    4
                                            ))
                                            .addLast(new LoggingHandler(LogLevel.DEBUG));
                                }
                            }
                    ).bind(9999).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
