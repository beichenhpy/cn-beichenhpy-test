package cn.beichenhpy.basic.netty.futureAndPromise.future;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote NettyFutureTest description：netty的future 存在于EventLoop中
 * @since 2021/5/26 10:42 下午
 */
@Slf4j
public class NettyFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //存在多个eventLoop 即 多个future
        NioEventLoopGroup group = new NioEventLoopGroup();
        Future<Integer> futureResult =
                group.submit(
                        new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                log.debug("正在计算");
                                Thread.sleep(1000);
                                return 50;
                            }
                        });
        log.debug("等待结果。。。");
//        //传统同步阻塞
//        Integer result = futureResult.get();
//        log.debug("结果是：{}",result);
        futureResult.addListener(
                new GenericFutureListener<Future<? super Integer>>() {
                    @Override
                    public void operationComplete(Future<? super Integer> future) throws Exception {
                        Integer result = (Integer) future.getNow();
                        log.debug("拿到结果:{}",result);
                        //结束
                        group.shutdownGracefully();
                    }
                }
        );

    }
}
