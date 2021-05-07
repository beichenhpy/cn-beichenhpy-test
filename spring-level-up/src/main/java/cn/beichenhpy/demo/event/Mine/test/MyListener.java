package cn.beichenhpy.demo.event.Mine.test;

import cn.beichenhpy.demo.event.Mine.Event;
import cn.beichenhpy.demo.event.Mine.Listener;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote MyListener description：自定义监听器
 * @since 2021/5/7 10:49 上午
 */
public class MyListener implements Listener<EventTest> {
    @Override
    public void doEvent(EventTest event) {
        System.out.println(Thread.currentThread().getName() + "myListener");
    }
}
