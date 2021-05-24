package cn.beichenhpy.basic.muti_thread.pool;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestThread description：
 * @since 2021/5/17 11:34 上午
 */
public class TestThread implements Runnable{
    @Override
    public  void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
