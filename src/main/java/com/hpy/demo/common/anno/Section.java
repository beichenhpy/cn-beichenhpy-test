package com.hpy.demo.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 用于设置section值
 * @since 2021/2/22 15:34
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Section {
}
