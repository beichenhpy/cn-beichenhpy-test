package com.hpy.demo.dbTest.controller;

import com.hpy.demo.dbTest.entity.DTO.PersonDTO;
import com.hpy.demo.common.Result;
import com.hpy.demo.dbTest.service.PersonService;
import org.springframework.web.bind.annotation.*;

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
