package cn.beichenhpy.netty.agreement.chat.protocol;

import cn.beichenhpy.netty.agreement.chat.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Test description：测试编解码
 * @since 2021/6/1 3:01 下午
 */
public class Test{
    public static void main(String[] args) throws Exception {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                //避免粘包半包
                new LengthFieldBasedFrameDecoder(
                        1024,
                        12,
                        4,
                        0,
                        0
                ),
                new MessageCodec()
        );
        //encode
        LoginRequestMessage message = new LoginRequestMessage("xiaoming", "123", "小明");
        embeddedChannel.writeOutbound(message);
        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);
        //切片验证半包问题
        ByteBuf part1 = buf.slice(0, 100);
        part1.retain();
        ByteBuf part2 = buf.slice(100, buf.readableBytes() - 100);
        part2.retain();
        embeddedChannel.writeInbound(part1);
        embeddedChannel.writeInbound(part2);
    }
}
