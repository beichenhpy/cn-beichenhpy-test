package cn.beichenhpy.netty.agreement.chat;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Message description：自定义协议
 * 要素：
 * - 魔数：用于在第一时间判定是否为无效的数据包----jvm class文件的魔数为  CAFEBABE
 * - 版本号 ： 可以支持升级的协议
 * - 序列化算法： 消息正文到此采用哪种序列化反序列化的方式，由此可以扩展：json、protobuf、hessian、jdk
 * - 消息指令：登录、注册、单聊、群聊和业务相关
 * - 指令序号：为双工通信，提供异步能力
 * - 正文长度：
 * - 消息正文：与序列化算法有关
 * @since 2021/6/1 2:17 下午
 */
@Data
public abstract class Message implements Serializable {
    private int sequenceId;
    private int messageType;

    public static Class<?> getMessageClass(int messageType){
        return messageClasses.get(messageType);
    }

    public abstract int getMessageType();
    //指令类型(消息类型
    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;

    private static final Map<Integer,Class<?>> messageClasses = new HashMap<>();
}
