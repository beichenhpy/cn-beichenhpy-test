package cn.beichenhpy.basic.muti_thread.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote CompletableFutureTest description：
 * @since 2021/6/18 18:25
 */
@Slf4j
public class CompletableFutureTest {


    static void sleep(long miles) {
        try {
            Thread.sleep(miles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * A进入餐厅，点菜，厨师做菜，吃饭
     *
     * @see CompletableFuture#supplyAsync(Supplier) 开启异步任务
     */
    @Test
    public void test1() {
        log.info("A进入餐厅");
        log.info("A点菜");
        //厨师做菜
        CompletableFuture<String> ctx = CompletableFuture.supplyAsync(
                () -> {
                    log.info("厨师做菜");
                    sleep(200);
                    return "西红柿炒鸡蛋";
                }
        );
        log.info("小明吃上了{}", ctx.join());
    }

    /**
     * A进入餐厅，点菜，厨师做菜，服务员盛饭，吃饭
     * 连接两个线程
     *
     * @see CompletableFuture#thenCompose(Function) 连接两个异步任务
     */
    @Test
    public void test2() {
        log.info("A进入餐厅");
        log.info("A点菜");
        //厨师做菜
        CompletableFuture<String> ctx = CompletableFuture.supplyAsync(() -> {
                    log.info("厨师做菜");
                    sleep(200);
                    return "西红柿炒鸡蛋";
                }
        ).thenCompose(
                dish -> CompletableFuture.supplyAsync(() -> {
                            log.info("服务员盛饭");
                            sleep(200);
                            return dish + "和米饭";
                        }
                )
        );
        log.info("小明吃上了{}", ctx.join());
    }

    /**
     * A进入餐厅，点菜，厨师做菜，服务员蒸饭，服务员盛饭，吃饭
     * 线程组合
     *
     * @see CompletableFuture#thenCombine(CompletionStage, BiFunction) 合并两个异步任务
     */
    @Test
    public void test3() {
        log.info("A进入餐厅");
        log.info("A点菜");
        CompletableFuture<String> ctx = CompletableFuture.supplyAsync(() -> {
                    log.info("厨师做菜");
                    sleep(200);
                    return "西红柿炒鸡蛋";
                }
        ).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("服务员蒸饭");
            sleep(300);
            return "米饭";
        }), (dish, rice) -> {
            log.info("服务员打饭");
            return dish + "和" + rice;
        });
        log.info("小明吃上了{}", ctx.join());
    }


    /**
     * 小明吃完饭结账，服务员开发票，找零钱，小明收到零钱
     *
     * @see CompletableFuture#thenApply(Function) 完成上一个异步任务的后置操作(同步)
     * @see CompletableFuture#thenApplyAsync(Function) 完成上一个异步任务的后置操作(异步)
     */
    @Test
    public void test4() {
        log.info("小明吃完饭了");
        log.info("小明去结账");
        log.info("小明打王者消遣");
        CompletableFuture<String> finish = CompletableFuture.supplyAsync(() -> {
            log.info("服务员开发票");
            sleep(200);
            return "发票";
        }).thenApply(ticket -> {
            log.info("服务员找零");
            sleep(200);
            return ticket + "和200元";
        });
        log.info("小明收到{}", finish.join());
    }


    /**
     * 小明坐公交车回家，616和600都可以选择 哪辆车先到，先做哪辆
     * 如果做了616 则会撞树上，异常处理 做出租车回家
     *
     * @see CompletableFuture#applyToEither(CompletionStage, Function) 两个异步任务，谁先完成，返回谁
     * @see CompletableFuture#exceptionally(Function) 异常处理
     */
    @Test
    public void test5() {
        CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            log.info("616公车到了");
            return "616";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            sleep(700);
            log.info("600公车到了");
            return "600";
        }), firstBus -> {
            if ("616".equals(firstBus)) {
                throw new RuntimeException("616撞树上了");
            }
            return firstBus;
        }).exceptionally(e -> {
            log.warn(e.getMessage());
            return "出租车";
        });
        log.info("小明做{}回家了", bus.join());
    }


}
