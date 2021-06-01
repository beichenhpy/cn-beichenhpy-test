package cn.beichenhpy.netty.agreement.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote LoginRequestMessage description：
 * @since 2021/6/1 2:31 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message{

    private String username;
    private String password;
    private String nickname;

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
