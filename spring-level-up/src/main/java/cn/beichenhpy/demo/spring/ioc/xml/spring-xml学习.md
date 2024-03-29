# Spring

## 核心API

### 应用

- ApplicationContext

  作用：对象的创建

  好处：解耦

  - ApplicationContext是接口，是一个工厂接口

    接口：屏蔽实现的差异

    ```java
    ClassPathXmlApplicationContext //非Web环境 main/junit
    XmlWebApplicationContext //web环境
    ```

  - 重量级资源

    - ApplicationContext工厂占用大量内存，不会频繁创建对象，一个应用只会创建一个对象。(单例)
    - ApplicationContext工厂一定是线程安全的。

- 具体开发

  1. 创建类型

  2. 配置文件的配置 `applicationContext.xml`

  3. 通过工厂类获取对象

     `ApplicationContext`--->`ClassPathXmlApplicationContext`

  ```java
  				//获取Spring的工厂
          ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
          //获取bean
          Person person = (Person) ctx.getBean("person");
          System.out.println(person);
  ```

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
          <!-- bean id(唯一) 和 类的全限定类名   -->
      <bean id="person" class="cn.beichenhpy.demo.spring.ioc.xml.bean.Person">
  
      </bean>
  </beans>
  ```

- ctx.getBean()的重载相关

  ```java
  //仅由class确定bean  注意 xml中的bean设置，不能有两个class都为Person的，否则报错 expect 1 return 2
  Person person1 = ctx.getBean(Person.class);
  // 由id 和 class 确定对应的bean
  Person person2 = ctx.getBean("person",Person.class);
  //获取Spring工厂配置文件中，所有bean的id值
  String[] beanNames = ctx.getBeanDefinitionNames();
  //按照类型获取bean的名字
  String[] persons = ctx.getBeanNamesForTypes(Person.class);
  // 判断是否有某个bean的Id
  boolean exist = ctx.containsBeanDefinition("person");
  //判断是否有某个bean
  boolean existBean = ctx.containsBean("person");
  ```

- 配置文件细节

  1. 只配置class属性

  ```xml
  <bean id="person" class="cn.beichenhpy.demo.spring.ioc.xml.bean.Person"/>
  ```

  - 上述配置 有id值 为：cn.beichenhpy.demo.spring.ioc.xml.bean.Person#0
  - 应用场景：这个bean只使用一次，则可以省略id

  2. name属性

  作用：为bean设置别名

  ```xml
  <bean id="person" name="personNick" class="cn.beichenhpy.demo.spring.ioc.xml.bean.Person"/>
  ```

  - 区别：

    - id和name，name可以定义多个，而id只能定义一个

    - 代码：

      ```java
      // 判断是否有某个bean的Id---只能判断Id不能判断name
      boolean exist = ctx.containsBeanDefinition("person");
      //判断是否有某个bean
      boolean existBean = ctx.containsBean("person");
      ```

      

  - 相同：id和name都不能重复

