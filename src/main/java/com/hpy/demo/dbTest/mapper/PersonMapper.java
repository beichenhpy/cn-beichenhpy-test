package com.hpy.demo.dbTest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hpy.demo.dbTest.entity.Person;
import com.hpy.demo.dbTest.modal.PersonInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author A51398
 */
@Repository
public interface PersonMapper extends BaseMapper<Person> {

    /**
     * 通过左连接获取对象集合
     * @return 返回对象集合
     */
    public List<PersonInfo> getPersonByJoin();

}
