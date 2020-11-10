package com.hpy.convertfoodemo;

import com.hpy.convertfoodemo.convert.entity.DO.PersonDO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ConvertFooDemoApplicationTests {

    @Test
    void contextLoads() {
        PersonDO personDO = new PersonDO().setBirthday(new Date()).setName("小明");
        PersonDTO personDTO = new PersonDTO();
        BeanUtils.copyProperties(personDO,personDTO);
        System.out.println(personDTO);
    }

}
