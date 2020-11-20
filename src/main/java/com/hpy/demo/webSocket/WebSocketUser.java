package com.hpy.demo.webSocket;

import lombok.Data;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * @author 韩鹏宇
 * @version 1.0
 * @description TODO 用于WebSocket的MessageHeaderAccessor使用
 * @since 2020/11/20 19:05
 */
@Data
public class WebSocketUser implements Principal {
    private final String name;
    @Override
    public String getName() {
        return name;
    }

    public WebSocketUser(String name) {
        this.name = name;
    }
}
