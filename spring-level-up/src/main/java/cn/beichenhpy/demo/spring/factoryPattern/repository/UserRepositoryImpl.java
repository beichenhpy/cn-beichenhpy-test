package cn.beichenhpy.demo.spring.factoryPattern.repository;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote UserRepositoryImpl description：
 * @since 2021/6/1 8:17 下午
 */
public class UserRepositoryImpl implements UserRepository{

    @Override
    public void addRepositoryUser() {
        System.out.println("数据库添加成功");
    }
}
