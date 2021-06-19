package cn.beichenhpy.webfluxdemo.controller;

import ch.qos.logback.core.util.TimeUtil;
import cn.beichenhpy.webfluxdemo.modal.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote SellTicketsController description：
 * @since 2021/6/19 14:25
 */
@Slf4j
@RestController
public class SellTicketsController {

    @PostMapping("/buyTicket")
    public Mono<Ticket> buyOne() throws InterruptedException {
        log.info("进入请求买票");
        //模拟耗时操作
        TimeUnit.SECONDS.sleep(2);
        log.info("返回票");
        return Mono.just(new Ticket("天津","北京",BigDecimal.valueOf(200)));
    }


}
