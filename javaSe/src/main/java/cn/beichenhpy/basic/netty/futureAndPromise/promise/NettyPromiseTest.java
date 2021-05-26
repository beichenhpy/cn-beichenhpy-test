package cn.beichenhpy.basic.netty.futureAndPromise.promise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote NettyPromiseTest description：继承与Netty的Future 可以放成功和失败的结果
 * @since 2021/5/26 10:50 下午
 */
@Slf4j
public class NettyPromiseTest {
    public static void main(String[] args) {
        //1.准备好EventLoop对象
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        //2.主动创建容器，存储结果
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
        new Thread(
                () -> {
                    try {
                        log.debug("开始计算");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //放置失败结果
                        promise.setFailure(e);
                    }
                    //放置成功结果
                    promise.setSuccess(60);
                }
        ).start();
        //3.接收
        log.debug("等待结果");
        promise.addListener(
                new GenericFutureListener<Future<? super Integer>>() {
                    @Override
                    public void operationComplete(Future<? super Integer> future) throws Exception {
                        log.debug("结果：{}",future.getNow());
                        //结束
                        group.shutdownGracefully();
                    }
                }
        );
    }
}
