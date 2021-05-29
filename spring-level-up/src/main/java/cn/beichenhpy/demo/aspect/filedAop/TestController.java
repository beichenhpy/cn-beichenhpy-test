package cn.beichenhpy.demo.aspect.filedAop;

import cn.beichenhpy.common.entity.PersonInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO
 * @since 2021/2/23 10:31
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public void test(@RequestBody PersonInfo personInfo){
        System.out.println(personInfo);
    }
}
