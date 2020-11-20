package com.hpy.demo.webSocket.config;

import com.hpy.demo.webSocket.WebSocketUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author A51398
 * @version 1.0
 * @description TODO websocket配置类 使用sock.js
 * @since 2020/11/17 15:37
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * @param registration: 注册信息
     * @return
     * @author 韩鹏宇
     * @since 2020/11/20 19:53
     * @description: TODO 增加拦截器，用于在连接前拦截，将头部信息中的key拿出来放入accessor中，目前还不适用于token环境
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())){
                    Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
                    if (raw instanceof Map){
                        Object appKey = ((Map<?, ?>) raw).get("appKey");
                        if (appKey instanceof LinkedList){
                            accessor.setUser(new WebSocketUser(((LinkedList<?>) appKey).get(0).toString()));
                        }
                        System.out.println("accessor:"+accessor);
                    }
                }
                return message;
            }
        });
    }
}
