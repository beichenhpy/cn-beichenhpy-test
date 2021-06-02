package cn.beichenhpy.spring;

import cn.beichenhpy.spring.anno.Component;
import cn.beichenhpy.spring.anno.ComponentScan;
import cn.beichenhpy.spring.anno.Inject;
import cn.beichenhpy.spring.anno.Scope;
import cn.beichenhpy.spring.aware.Aware;
import cn.beichenhpy.spring.aware.BeanNameAware;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote ApplicationContext description：
 * @since 2021/6/2 10:49 上午
 */
public class ApplicationContext {

    private Class<?> configClass;
    //单例池
    public final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    //BeanDefinition信息池
    public final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //bean前后置处理器
    List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
    public static final String CLASS = "classes";
    private static final String SEPARATOR = System.getProperty("file.separator");
    public static final String SINGLETON = "singleton";
    public static final String PROPERTY = "property";

    /**
     * 将 a/b/c.class -> a.b.c.class
     *
     * @param file 文件
     * @return 返回bean的路径
     */
    private String getBeanPath(File file) {
        String beanPath = file.getAbsolutePath();
        int indexOfClass = beanPath.indexOf(CLASS) + CLASS.length() + 1;
        String newBeanPath = beanPath.substring(indexOfClass).replace(SEPARATOR, ".");
        return newBeanPath.substring(0, newBeanPath.lastIndexOf("."));
    }

    /**
     * 获取类名开头小写的beanName
     *
     * @param beanPath bean的path
     * @return 返回name
     */
    private String getDefaultBeanName(String beanPath) {
        String beanName = beanPath.substring(beanPath.lastIndexOf(".") + 1);
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }


    public ApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        //扫描 将bean放入BeanDefinitionMap中
        scan();
        //新建单例Bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (SINGLETON.equals(beanDefinition.getScope())) {
                Object bean = createBean(beanName, beanDefinition);
                //bean不能为null
                if (bean == null){
                    throw new RuntimeException("bean初始化失败");
                }
                singletonObjects.put(beanName, bean);
            }
        }

    }

    /**
     * 创建bean
     *
     * @param beanName       bean名字
     * @param beanDefinition bean定义
     * @return 返回bean对象
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazz();
        try {

            Object instance = beanClass.getDeclaredConstructor().newInstance();
            //给bean进行依赖注入
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    //有注解 判断值name
                    Inject inject = field.getDeclaredAnnotation(Inject.class);
                    String name = inject.name();
                    field.setAccessible(true);
                    if ("".equals(name)) {
                        //默认注入 byName
                        field.set(instance, getBean(field.getName()));
                    } else {
                        field.set(instance, getBean(name));
                    }
                }
            }
            //执行所有的Aware接口 Aware回调
            doAware(beanName, instance);

            //BeanPostProcessor 前置处理
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance,beanName);
            }
            //初始化
            doInitialize(instance);

            //BeanPostProcessor 后置处理 aop是基于后置处理实现的
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance,beanName);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行左右的aware接口
     *
     * @param beanName beanName
     * @param bean     bean实例
     */
    private void doAware(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
    }

    /**
     * 执行初始化
     *
     * @param bean bean对象
     */
    private void doInitialize(Object bean) throws Exception{
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

    private void scan() {
        //解析configClass
        //读取扫描component的路径
        ComponentScan componentScan = configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScan.value();
        if ("".equals(path)) {
            throw new RuntimeException("未指定Bean扫描路径");
        }
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(path.replace(".", SEPARATOR));
        if (url != null) {
            File file = new File(url.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        //获取bean的路径
                        String newBeanPath = getBeanPath(f);
                        try {
                            Class<?> beanClass = classLoader.loadClass(newBeanPath);
                            if (beanClass.isAnnotationPresent(Component.class)) {
                                //判断beanName是否存在
                                Component component = beanClass.getDeclaredAnnotation(Component.class);
                                String beanName = component.value();
                                //未设置bean Name
                                if ("".equals(beanName)) {
                                    beanName = getDefaultBeanName(newBeanPath);
                                }

                                BeanDefinition beanDefinition = new BeanDefinition();
                                //判断作用域 不存在则为Singleton
                                String scope;
                                if (beanClass.isAnnotationPresent(Scope.class)) {
                                    //有注解，读value
                                    Scope scopeAnnotation = beanClass.getDeclaredAnnotation(Scope.class);
                                    scope = scopeAnnotation.value();
                                } else {
                                    scope = SINGLETON;
                                }
                                beanDefinition.setClazz(beanClass);
                                beanDefinition.setScope(scope);
                                beanDefinitionMap.put(beanName, beanDefinition);

                                //注册beanPostProcessor 使用createBean 如果使用getBean如果是单例的会返回null
                                if (BeanPostProcessor.class.isAssignableFrom(beanClass)){
                                    BeanPostProcessor bean = (BeanPostProcessor) createBean(beanName, beanDefinition);
                                    beanPostProcessors.add(bean);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        Object bean;
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            String scope = beanDefinition.getScope();
            if (SINGLETON.equals(scope)) {
                bean = singletonObjects.get(beanName);
            } else {
                bean = createBean(beanName, beanDefinition);
            }
        } else {
            throw new RuntimeException("不存在此Bean");
        }
        return bean;
    }
}
