package cn.beichenhpy.demo.filter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote VersionController description：
 * @since 2021/6/15 21:57
 */
@RestController
public class VersionController {

    @GetMapping("/version/get")
    public ResponseEntity<String> getTest(){
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/v1/version/get")
    public ResponseEntity<String> getTest1(){
        return ResponseEntity.ok("ok-v1");
    }


    @GetMapping("/v2/version/get")
    public ResponseEntity<String> getTest2(){
        return ResponseEntity.ok("ok-v2");
    }

}
