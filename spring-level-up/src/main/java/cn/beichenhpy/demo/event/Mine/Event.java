package cn.beichenhpy.demo.event.Mine;

import java.util.EventObject;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Event description：事件
 * @since 2021/5/7 10:30 上午
 */
public class Event extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public Event(Object source) {
        super(source);
    }
}
