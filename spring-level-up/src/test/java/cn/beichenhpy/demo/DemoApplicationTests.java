package cn.beichenhpy.demo;

import cn.beichenhpy.demo.event.Mine.test.MyEventTest;
import cn.beichenhpy.demo.event.spring.PrintController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private PrintController printController;
    @Resource
    private MyEventTest myEventTest;
    @Test
    void contextLoads() {
        printController.test();
    }

    @Test
    void test(){
        myEventTest.test();
    }

}
