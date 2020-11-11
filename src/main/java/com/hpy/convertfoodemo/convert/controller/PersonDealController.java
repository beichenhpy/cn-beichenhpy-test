package com.hpy.convertfoodemo.convert.controller;

import com.hpy.convertfoodemo.convert.entity.DTO.PersonDTO;
import com.hpy.convertfoodemo.convert.entity.Result;
import com.hpy.convertfoodemo.convert.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PersonService personService;

    public PersonDealController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * 分页演示
     * 因为分页需要page的相关信息，因此将DTO放在service层进行包装后返回
     * @param current 当前页
     * @param size 显示大小
     * @return 返回结果类
     */
    @GetMapping("/getList")
    public Result<?> getList(@RequestParam Integer current,@RequestParam Integer size) {
        return Result.ok(personService.getList(current,size));
    }

    /**
     * 插入演示
     * 传入参数需要为DTO实体
     * 控制层可以对传入的数据进行增加或修改
     */
    @PostMapping("/add")
    public Result<?> addPerson(@RequestBody PersonDTO personDTO){
        /*
         *举例：可能这里传进来的date类型不满足条件
         *那么就需要进行对DTO进行处理
         */
        Date birthday = personDTO.getBirthday();
        Date afterBirthday;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            afterBirthday = simpleDateFormat.parse(simpleDateFormat.format(birthday));
        } catch (ParseException e) {
            return Result.error(e.getMessage());
        }
        personDTO.setBirthday(afterBirthday);
        personService.addPerson(personDTO);
        return Result.ok();
    }
}
