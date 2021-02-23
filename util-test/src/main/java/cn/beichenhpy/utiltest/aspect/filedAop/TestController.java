package cn.beichenhpy.utiltest.aspect.filedAop;

import cn.beichenhpy.common.entity.PersonInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO
 * @since 2021/2/23 10:31
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public void test(@RequestBody PersonInfo personInfo){
        System.out.println(personInfo);
    }
}
