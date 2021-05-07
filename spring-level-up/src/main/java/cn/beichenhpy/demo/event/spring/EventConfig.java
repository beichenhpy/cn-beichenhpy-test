package cn.beichenhpy.demo.event.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventConfig description：
 * @since 2021/5/7 9:39 上午
 */
@Configuration
public class EventConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    /**
     * 核心线程数
     * 默认的核心线程数为1
     *
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程数
     * 默认的最大线程数是Integer.MAX_VALUE 即2<sup>31</sup>-1
     */
    private static final int MAX_POOL_SIZE = 50;

    /**
     * 缓冲队列数
     * 默认的缓冲队列数是Integer.MAX_VALUE 即2<sup>31</sup>-1
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 允许线程空闲时间
     * 默认的线程空闲时间为60秒
     */
    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     * 线程池前缀名
     */
    private static final String THREAD_NAME_PREFIX = "Task_Service_Async_";

    /**
     * allowCoreThreadTimeOut为true则线程池数量最后销毁到0个
     * allowCoreThreadTimeOut为false
     * 销毁机制：超过核心线程数时，而且（超过最大值或者timeout过），就会销毁。
     * 默认是false
     */
    private static final boolean ALLOW_CORE_THREAD_TIME_OUT = false;

    @Bean("taskExecutor")
    @Order(1)
    public ThreadPoolTaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        taskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        taskExecutor.setAllowCoreThreadTimeOut(ALLOW_CORE_THREAD_TIME_OUT);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池初始化
        taskExecutor.initialize();
        return taskExecutor;
    }


    /**
     *
     * 可以自定义异步、或者使用@Aysnc修饰在Listener实现类,并且启用Async @EnableAsync，onApplicationEvent方法上
     * @see org.springframework.context.ApplicationListener
     * @see SimpleApplicationEventMulticaster#setTaskExecutor(Executor)
     */
    @Bean
    @Order(2)
    public ApplicationEventMulticaster applicationEventMulticaster(){
        Executor taskExecutor = applicationContext.getBean("taskExecutor", ThreadPoolTaskExecutor.class);
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        //默认设置的的同步的执行
        //simpleApplicationEventMulticaster.setTaskExecutor(new SyncTaskExecutor());
        simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor);
        return simpleApplicationEventMulticaster;
    }
}
