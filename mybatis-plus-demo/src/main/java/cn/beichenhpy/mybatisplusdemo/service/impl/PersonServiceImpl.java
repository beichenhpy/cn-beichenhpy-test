package cn.beichenhpy.mybatisplusdemo.service.impl;

import cn.beichenhpy.mybatisplusdemo.modal.Person;
import cn.beichenhpy.mybatisplusdemo.mapper.AddressMapper;
import cn.beichenhpy.mybatisplusdemo.mapper.PersonMapper;
import cn.beichenhpy.mybatisplusdemo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
