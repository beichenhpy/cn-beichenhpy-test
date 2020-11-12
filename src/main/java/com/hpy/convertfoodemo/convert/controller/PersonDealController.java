package com.hpy.convertfoodemo.convert.controller;

import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.common.Result;
import com.hpy.convertfoodemo.convert.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author A51398
 */
@RestController
@RequestMapping("/api")
public class PersonDealController {
    /**
     *关于依赖注入
     *如果强制依赖最好使用此方法注入，即构造方法注入
     * 不强制可以使用set方法注入
     */
    private final PersonService personService;

    public PersonDealController(PersonService personService) {
        this.personService = personService;
    }


    /**
     * 插入演示
     * 传入参数需要为DTO实体
     * 控制层可以对传入的数据进行增加或修改
     */
    @PostMapping("/add")
    public Result<?> addPerson(@RequestBody PersonDTO personDTO){
        /*
         *举例：
         * 对DTO做简单的处理
         *
         */
        personService.addPerson(personDTO);
        return Result.ok();
    }

    @GetMapping("/getByJoin")
    public Result<?> getByJoin(){
        return Result.ok(personService.getPersonByJoin());
    }
    @GetMapping("/getByJava")
    public Result<?> getByJava(){
        return Result.ok(personService.getPersonByJava());
    }
}
