package cn.beichenhpy.spring.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Scope description：bean作用域 singleton/property
 * @since 2021/6/2 11:03 上午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
    String value();
}
