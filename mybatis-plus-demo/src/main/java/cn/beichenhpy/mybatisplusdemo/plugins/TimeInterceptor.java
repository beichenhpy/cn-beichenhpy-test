package cn.beichenhpy.mybatisplusdemo.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TimeInterceptor description：add createTime when update/insert
 * @since 2021/5/29 8:01 下午
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class,Object.class}
        )
)
@Component
public class TimeInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        SqlCommandType type = mappedStatement.getSqlCommandType();
        Object parameter = args[1];
        if (type.equals(SqlCommandType.INSERT)){
            Class<?> clazz = parameter.getClass();
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if ("createTime".equals(field.getName())){
                    field.setAccessible(true);
                    Object oldDate = field.get(parameter);
                    field.setAccessible(false);
                    if (oldDate == null || oldDate == ""){
                        field.setAccessible(true);
                        field.set("createTime",new Date());
                        field.setAccessible(false);
                    }
                }
            }
        }else{
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        if (o instanceof Executor){
            return Plugin.wrap(o,this);
        }
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
