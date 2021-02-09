package com.hpy.demo.dbTest.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.demo.dbTest.entity.Address;
import com.hpy.demo.dbTest.entity.Person;
import com.hpy.demo.dbTest.modal.PersonInfo;
import com.hpy.demo.dbTest.mapper.AddressMapper;
import com.hpy.demo.dbTest.mapper.PersonMapper;
import com.hpy.demo.dbTest.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A51398
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;

    public PersonServiceImpl(PersonMapper personMapper,AddressMapper addressMapper) {
        this.personMapper = personMapper;
        this.addressMapper = addressMapper;
    }


    @Override
    public void personLog() {
        log.warn(Thread.currentThread().getName()+"-----------------personLog");
        List<Person> people = personMapper.selectList(null);
        log.warn("person:{}",people);
    }
}
