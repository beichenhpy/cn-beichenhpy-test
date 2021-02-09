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

}
