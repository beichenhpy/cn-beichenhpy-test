package com.hpy.demo.dbTest.controller;

import com.hpy.demo.dbTest.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 测试静态变量注入 使用构造函数
 * @since 2021/2/20 17:23
 */
@Component
public class StaticInjectTest {

    private static AddressService addressService;
    @Autowired
    public StaticInjectTest(AddressService addressService){
        StaticInjectTest.addressService = addressService;
    }

    public static void test(){
        addressService.addressLog();
    }
}
