package cn.beichenhpy.stompwebsocket;

import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
    @Autowired
    private SimpUserRegistry simpUserRegistry;
    @RequestMapping("/TestSend")
    public void testSend(){
        simpMessagingTemplate.convertAndSend("/topic/hello", JSONUtil.toJsonStr("你好"));
    }

    /**
     * @param accessor: message请求头，可以拿到头部信息
     *
     * @author 韩鹏宇
     * @since 2020/11/20 19:51
     * @description: TODO 测试点对点发送，@SendToUser需要结合Shiro等工具
     */
    @MessageMapping("/hello")
    public void foo(SimpMessageHeaderAccessor accessor){
        Principal user = accessor.getUser();
        System.out.println(user);
        simpMessagingTemplate.convertAndSendToUser(user.getName(),"/topic/hello","点对点测试");
    }
}
