package cn.beichenhpy.basic.muti_thread.pool;

import java.util.concurrent.*;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PoolTest description：
 * @since 2021/5/17 11:35 上午
 */
public class PoolTest {

    private static final int CORE_THREAD = 10;
    private static final int MAX_POOL_THREAD = 10;
    private static final Long KEEP_ALIVE_TIME = 1L;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();
    private static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();
    private static final RejectedExecutionHandler REJECTED_EXECUTION_HANDLER = new ThreadPoolExecutor.AbortPolicy();

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_THREAD,
                MAX_POOL_THREAD,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE,
                THREAD_FACTORY,
                REJECTED_EXECUTION_HANDLER
        );
        for (int i = 0; i < 20; i++) {
            executor.execute(new TestThread());
        }
    }
}
