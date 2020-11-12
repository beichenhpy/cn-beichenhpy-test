package com.hpy.convertfoodemo.convert.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonInfoDTO;

import java.util.List;

/**
 * @author A51398
 */
public interface PersonService {


    /**
     * 添加用户
     * @param personDTO 用户实体类
     */
    void addPerson(PersonDTO personDTO);

    /**
     * 通过左连接获取用户信息集合
     * @return 用户信息集合
     */
    Page<PersonInfoDTO>getPersonByJoin();

    /**
     * 通过java拼接获得用户集合
     * @return 用户信息集合
     */
    Page<PersonInfoDTO> getPersonByJava();
}
