package cn.beichenhpy.demo.event.Mine.test;

import cn.beichenhpy.demo.event.Mine.Event;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventTest description：事件测试类
 * @since 2021/5/7 10:48 上午
 */
public class EventTest extends Event {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EventTest(Object source) {
        super(source);
    }
}
