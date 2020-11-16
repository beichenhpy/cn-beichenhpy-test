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
     * 通过java拼接获得用户集合
     * @return 用户信息集合
     */
    Page<PersonInfo> getPersonByJava();
}
