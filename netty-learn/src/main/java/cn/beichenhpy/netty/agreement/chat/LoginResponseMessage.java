package cn.beichenhpy.netty.agreement.chat;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote LoginRequestMessage description：
 * @since 2021/6/1 2:31 下午
 */
public class LoginResponseMessage extends Message{
    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
