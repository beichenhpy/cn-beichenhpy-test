package com.hpy.demo.dbTest.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.demo.dbTest.modal.PersonInfo;

/**
 * @author A51398
 */
public interface PersonService {

    /**
     * 通过左连接获取用户信息集合
     * @return 用户信息集合
     */
    Page<PersonInfo>getPersonByJoin();

    /**

     * @return 用户信息集合
     * @author 韩鹏宇
     * @since 2020/11/17 20:14
     * @description: TODO
     */
    Page<PersonInfo> getPersonByJava();
}
