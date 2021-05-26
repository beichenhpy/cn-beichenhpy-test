package cn.beichenhpy.basic.netty.futureAndPromise.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote JdkFutureTest description：jdk中的Future
 * @since 2021/5/26 10:32 下午
 */
@Slf4j
public class JdkFutureTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> taskResult = service.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        log.debug("执行计算");
                        Thread.sleep(1000);
                        return "ok";
                    }
                }
        );
        log.debug("等待结果。。。");
        try {
            String result = taskResult.get();
            log.debug("结果：{}",result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //结束
        service.shutdown();
    }
}
