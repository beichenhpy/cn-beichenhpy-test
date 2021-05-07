package cn.beichenhpy.demo.event.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PrintEvent description：具体事件源
 * @since 2021/5/7 8:59 上午
 */
@Getter
@Setter
public class PrintEvent extends ApplicationEvent {

    private String name;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public PrintEvent(Object source,String name) {
        super(source);
        this.name = name;
    }
}
