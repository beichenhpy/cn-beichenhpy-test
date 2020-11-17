package com.hpy.demo.webSocket;

import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/11/17 15:53
 */
@RestController
public class SendController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/TestSend")
    public void testSend(){
        simpMessagingTemplate.convertAndSend("/topic/greetings", JSONUtil.toJsonStr("你好"));
    }
}
