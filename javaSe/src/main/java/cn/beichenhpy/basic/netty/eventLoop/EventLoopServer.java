package cn.beichenhpy.basic.netty.eventLoop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventLoopServer description：eventLoop服务端
 * <br> 注意：记得关闭所有的EventLoopGroup连接 {@link EventLoopGroup#shutdownGracefully()}
 * @since 2021/5/26 4:15 下午
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        //细分2：将耗时较久的操作用单独的eventLoopGroup处理
        EventLoopGroup costTimeGroup = new DefaultEventLoop();
        new ServerBootstrap()
                //第一个参数 parentEventGroup 处理 accept 事件  第二个参数 childEventGroup 处理 read/write事件
                .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast("nioGroup",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        log.debug(buf.toString(Charset.defaultCharset()));
                                        /**
                                         * 将消息传递给后面的handler 相当于换人操作
                                         * @see AbstractChannelHandlerContext#invokeChannelRead(AbstractChannelHandlerContext, Object)
                                         * static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
                                         *         final Object m = next.pipeline.touch(ObjectUtil.checkNotNull(msg, "msg"), next);
                                         *         //查询下一个线程的eventLoop
                                         *         EventExecutor executor = next.executor();
                                         *         //判断是否和下一个eventLoop的handler是同一个线程？
                                         *         if (executor.inEventLoop()) {
                                         *              //是，同一个线程则直接调用
                                         *             next.invokeChannelRead(m);
                                         *         } else {
                                         *            //否，则起一个新的线程执行 (ex 从 NioEventLoop -> DefaultEventLoop)
                                         *             executor.execute(new Runnable() {
                                         *                 @Override
                                         *                 public void run() {
                                         *                     next.invokeChannelRead(m);
                                         *                 }
                                         *             });
                                         *         }
                                         *     }
                                         *   如果俩个Handler绑定的是同一个EventLoop 那么就直接调用
                                         *   否则要讲要调用的代码封装到一个任务，由下一个handler的EventLoop调用
                                         */
                                        ctx.fireChannelRead(msg);
                                    }
                                    //设置耗时任务的eventLoopGroup 和 name
                                }).addLast(costTimeGroup,"costTimeGroup",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        //模拟长时间操作
                                        Thread.sleep(10000);
                                        log.debug(buf.toString(Charset.defaultCharset()));
                                    }
                                });
                            }
                        }
                ).bind(9999);
    }
}
