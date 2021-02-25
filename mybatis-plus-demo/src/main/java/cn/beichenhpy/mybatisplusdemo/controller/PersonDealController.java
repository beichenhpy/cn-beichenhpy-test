package cn.beichenhpy.mybatisplusdemo.controller;

import cn.beichenhpy.common.entity.PersonInfo;
import cn.beichenhpy.mybatisplusdemo.service.PersonService;
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



    @PostMapping("/add")
    public void add(@RequestBody PersonInfo personInfo){
        System.out.println(personInfo);
    }

}
