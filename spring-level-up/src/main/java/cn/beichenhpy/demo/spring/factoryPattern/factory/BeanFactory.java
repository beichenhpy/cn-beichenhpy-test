package cn.beichenhpy.demo.spring.factoryPattern.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote BeanFactory description：
 * @since 2021/6/1 7:53 下午
 */
public class BeanFactory {

    //读取操作使用IO所以作为类加载
    private static final Properties env = new Properties();

    static{
        //读取文件
        try(InputStream in = BeanFactory.class.getResourceAsStream("/applicationContext.properties")) {
            //写入到Properties
            env.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 通过全限定类名的方式获取具体的实现类
//     * step up - 使用配置文件读取
//     * @return userService
//     */
//    public static UserService getUserService(){
//        UserService userService = null;
//        try {
//            Class<?> clazz = Class.forName(env.getProperty("userService"));
//            userService = ((UserService) clazz.getDeclaredConstructor().newInstance());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return userService;
//    }
//
//    /**
//     * 通过全限定类名的方式获取具体的实现类
//     * step up - 使用配置文件读取
//     * @return userRepository
//     */
//    public static UserRepository getUserRepository(){
//        UserRepository userRepository = null;
//        try {
//            Class<?> clazz = Class.forName(env.getProperty("userRepository"));
//            userRepository = (UserRepository) clazz.getDeclaredConstructor().newInstance();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return userRepository;
//    }

    /**
     * 通用getBean对象方法
     * @param beanName bean名
     * @return 返回Object
     */
    public static Object getBean(String beanName){
        Object bean = null;
        try {
            Class<?> clazz = Class.forName(env.getProperty(beanName));
            bean = clazz.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
