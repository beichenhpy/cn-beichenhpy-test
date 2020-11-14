package com.hpy.convertfoodemo.convert.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpy.convertfoodemo.convert.entity.DO.AddressDO;
import com.hpy.convertfoodemo.convert.entity.DO.PersonDO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonInfoDTO;
import com.hpy.convertfoodemo.convert.mapper.AddressMapper;
import com.hpy.convertfoodemo.convert.mapper.PersonMapper;
import com.hpy.convertfoodemo.convert.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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

        //转换
        PersonDO personDO = new PersonDO();
        BeanUtils.copyProperties(personDTO,personDO);
        personMapper.insert(personDO);
    }


    @Override
    public Page<PersonInfoDTO> getPersonByJoin() {
        long startTime = System.currentTimeMillis();
        List<PersonInfoDTO> personByJoin = personMapper.getPersonByJoin();
        Page<PersonInfoDTO> personInfoDTOPage = new Page<>();
        personInfoDTOPage.setRecords(personByJoin);
        long endTime = System.currentTimeMillis();
        long usedTime  = endTime - startTime;
        System.out.println(usedTime);
        return personInfoDTOPage;
    }

    @Override
    public Page<PersonInfoDTO> getPersonByJava() {
        long startTime = System.currentTimeMillis();
        IPage<PersonDO> personPage = personMapper.selectPage(new Page<>(),null);
        IPage<AddressDO> addressPage = addressMapper.selectPage(new Page<>(),null);
        //todo 获取person信息
        List<PersonDO> personDOList = personPage.getRecords();
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
       //todo 获取所有address信息
        List<AddressDO> addressDOList = addressPage.getRecords();
        int personSize = personDOList.size();
        int addressSize = addressDOList.size();
        for (int i = 0; i < personSize ; i++) {
            PersonDO personDO = personDOList.get(i);
            PersonInfoDTO personInfoDTO = new PersonInfoDTO();
            personInfoDTO.setName(personDO.getName());
            personInfoDTO.setSex(personDO.getSex());
            personInfoDTO.setId(personDO.getId());
            for (int j = 0; j < addressSize; j++) {
                AddressDO addressDO = addressDOList.get(j);
                if (addressDO.getName().equals(personInfoDTO.getName())){
                    personInfoDTO.setAddress(addressDO.getAddress());
                    break;
                }
            }
            personInfoDTOList.add(personInfoDTO);
        }
        Page<PersonInfoDTO> personInfoDTOPage = new Page<>();
        personInfoDTOPage.setRecords(personInfoDTOList);
        long endTime = System.currentTimeMillis();
        long usedTime  = endTime - startTime;
        System.out.println(usedTime);
        return personInfoDTOPage;
    }
}
