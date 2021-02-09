package com.hpy.demo.dbTest.controller;

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
        personService.personLog();
    }


}
