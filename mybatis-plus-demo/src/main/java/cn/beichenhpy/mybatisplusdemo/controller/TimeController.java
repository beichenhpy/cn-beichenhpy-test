package cn.beichenhpy.mybatisplusdemo.controller;

import cn.beichenhpy.common.entity.Result;
import cn.beichenhpy.mybatisplusdemo.modal.TimeTest;
import cn.beichenhpy.mybatisplusdemo.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time")
public class TimeController {
    @Autowired
    private TimeService timeService;

    @PostMapping("/add")
    public Result<?> add(@RequestBody TimeTest timeTest){
        timeService.save(timeTest);
        return Result.ok();
    }

    @GetMapping("list")
    public Result<?> list(){
        return Result.ok(timeService.list());
    }
}
