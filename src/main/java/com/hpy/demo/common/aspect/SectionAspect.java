package com.hpy.demo.common.aspect;

import com.hpy.demo.common.anno.Section;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 在所有controller中找到@Section修饰的成员变量 赋值
 * @since 2021/2/22 15:37
 */
@Aspect
@Component
public class SectionAspect {
    @Pointcut("execution(public * com.hpy.demo..*.*Controller.*(..))")
    public void logPointCut() {

    }
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (Object arg : args) {
            Class<?> argClass = arg.getClass();
            Field[] fields = argClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Section.class) != null){
                    field.setAccessible(true);
                    //可以改为数据库查询
                    field.set(arg,"test");
                }
            }
        }
        //执行方法
        return point.proceed();
    }
}
