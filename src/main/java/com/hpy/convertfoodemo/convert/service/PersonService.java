package com.hpy.convertfoodemo.convert.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.convert.entity.VO.PersonVO;

/**
 * @author A51398
 */
public interface PersonService {

    /**
     * Vo分页
     * @param current 当前页
     * @param size 页面大小
     * @return VO
     */
    Page<PersonVO> getList(Integer current,Integer size);

    /**
     * 添加用户
     * @param personDTO 用户实体类
     */
    void addPerson(PersonDTO personDTO);

}
