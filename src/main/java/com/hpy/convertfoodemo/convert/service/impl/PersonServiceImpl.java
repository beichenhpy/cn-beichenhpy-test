package com.hpy.convertfoodemo.convert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.convertfoodemo.convert.entity.DO.PersonDO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.convert.entity.VO.PersonVO;
import com.hpy.convertfoodemo.convert.mapper.PersonMapper;
import com.hpy.convertfoodemo.convert.service.PersonService;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author A51398
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;
    @Override
    public Page<PersonVO> getList(Integer current,Integer size) {
        //模拟前端传page 和 size
        Page<PersonDO> page = new Page<>(current,size);
        //查询到的Ipage分页信息
        IPage<PersonDO> selectPage = personMapper.selectPage(page, null);
        //取出DO
        List<PersonDO> records = selectPage.getRecords();
        //转换
        List<PersonVO> personVOList = new ArrayList<>();
        for (PersonDO record : records) {
            PersonVO personVO = new PersonVO();
            BeanUtils.copyProperties(record,personVO);
            personVOList.add(personVO);
        }
        //新建PageVo
        Page<PersonVO> voPage = new Page<>();
        voPage.setRecords(personVOList);
        voPage.setCurrent(selectPage.getCurrent());
        voPage.setSize(selectPage.getSize());
        voPage.setTotal(selectPage.getTotal());
        return voPage;
    }

    /**
     * 主要业务实现
     * @param personDTO 用户实体类
     */
    @Override
    public void addPerson(PersonDTO personDTO) {
        /*
         * 可以进行对数据库的储存
         * redis处理。。。
         */

        /*
         * 将业务处理 比如将密码 加密后存入
         */
        //模拟加密密码
        personDTO.setPassword(personDTO.getPassword() + UUID.randomUUID().toString());
        //转换
        PersonDO personDO = new PersonDO();
        BeanUtils.copyProperties(personDTO,personDO);
        personMapper.insert(personDO);
    }
}
