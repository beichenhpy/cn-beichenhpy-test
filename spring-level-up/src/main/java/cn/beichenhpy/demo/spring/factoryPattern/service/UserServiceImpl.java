package cn.beichenhpy.demo.spring.factoryPattern.service;

import cn.beichenhpy.demo.spring.factoryPattern.factory.BeanFactory;
import cn.beichenhpy.demo.spring.factoryPattern.repository.UserRepository;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote UserServiceImpl description：
 * @since 2021/6/1 7:52 下午
 */
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository = (UserRepository) BeanFactory.getBean("userRepository");
    @Override
    public void addUser() {
        userRepository.addRepositoryUser();
        System.out.println("添加新用户");
    }
}
