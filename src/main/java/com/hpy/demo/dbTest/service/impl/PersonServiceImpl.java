package com.hpy.demo.dbTest.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.demo.dbTest.entity.Address;
import com.hpy.demo.dbTest.entity.Person;
import com.hpy.demo.dbTest.modal.PersonInfo;
import com.hpy.demo.dbTest.mapper.AddressMapper;
import com.hpy.demo.dbTest.mapper.PersonMapper;
import com.hpy.demo.dbTest.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A51398
 */
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;

    public PersonServiceImpl(PersonMapper personMapper,AddressMapper addressMapper) {
        this.personMapper = personMapper;
        this.addressMapper = addressMapper;
    }




    @Override
    public Page<PersonInfo> getPersonByJoin() {
        long startTime = System.currentTimeMillis();
        List<PersonInfo> personByJoin = personMapper.getPersonByJoin();
        Page<PersonInfo> personInfoDTOPage = new Page<>();
        personInfoDTOPage.setRecords(personByJoin);
        long endTime = System.currentTimeMillis();
        long usedTime  = endTime - startTime;
        System.out.println(usedTime);
        return personInfoDTOPage;
    }

    @Override
    public Page<PersonInfo> getPersonByJava() {
        long startTime = System.currentTimeMillis();
        IPage<Person> personPage = personMapper.selectPage(new Page<>(1,10),null);
        IPage<Address> addressPage = addressMapper.selectPage(new Page<>(1,10),null);
        //todo 获取person信息
        List<Person> personList = personPage.getRecords();
        List<PersonInfo> personInfoList = new ArrayList<>();
       //todo 获取所有address信息
        List<Address> addressList = addressPage.getRecords();
        int personSize = personList.size();
        int addressSize = addressList.size();
        for (int i = 0; i < personSize ; i++) {
            Person person = personList.get(i);
            PersonInfo personInfo = new PersonInfo();
            personInfo.setName(person.getName());
            personInfo.setSex(person.getSex());
            personInfo.setId(person.getId());
            for (int j = 0; j < addressSize; j++) {
                Address address = addressList.get(j);
                if (address.getName().equals(personInfo.getName())){
                    personInfo.setAddress(address.getAddress());
                    break;
                }
            }
            personInfoList.add(personInfo);
        }
        Page<PersonInfo> personInfoDTOPage = new Page<>();
        personInfoDTOPage.setRecords(personInfoList);
        long endTime = System.currentTimeMillis();
        long usedTime  = endTime - startTime;
        System.out.println(usedTime);
        return personInfoDTOPage;
    }
}
