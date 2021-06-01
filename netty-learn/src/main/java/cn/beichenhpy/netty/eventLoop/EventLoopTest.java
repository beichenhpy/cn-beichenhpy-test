package cn.beichenhpy.netty.eventLoop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventLoopTest description：EventLoop测试
 * <br>两条继承线
 * <br>1、juc 下的 {@link java.util.concurrent.ScheduledExecutorService}
 * <br>2、netty 自己的 {@link io.netty.util.concurrent.OrderedEventExecutor}
 * <br>所以拥有了线程池的所有的方法,可以做一些异步的耗时操作
 * @see io.netty.channel.EventLoopGroup
 * @since 2021/5/26 3:48 下午
 */
@Slf4j
public class EventLoopTest {

    public static void main(String[] args) {
        //io事件、普通任务、定时任务
        EventLoopGroup group = new NioEventLoopGroup(2);
        //获取下一个事件循环对象 提供了两个线程，所以循环调用
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        //执行普通任务
        group.next().submit(
                ()->{
                    log.debug("线程执行");
                }
        );

        //执行定时任务
        group.next().scheduleAtFixedRate(
                ()->{log.debug("执行定时任务");},
                1,
                1,
                TimeUnit.SECONDS
        );
    }

}
