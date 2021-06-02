package cn.beichenhpy.spring.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Inject description：注入
 * @since 2021/6/2 11:03 上午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
    String name() default "";
}
