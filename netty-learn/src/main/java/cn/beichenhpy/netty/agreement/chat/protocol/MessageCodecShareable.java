package cn.beichenhpy.netty.agreement.chat.protocol;

import cn.beichenhpy.netty.agreement.chat.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote MessageCodec description：自定义编解码器
 * 要素：
 * - 魔数：用于在第一时间判定是否为无效的数据包----jvm class文件的魔数为  CAFEBABE
 * - 版本号 ： 可以支持升级的协议
 * - 序列化算法： 消息正文到此采用哪种序列化反序列化的方式，由此可以扩展：json、protobuf、hessian、jdk
 * - 消息指令：登录、注册、单聊、群聊和业务相关
 * - 指令序号：为双工通信，提供异步能力
 * - 正文长度：
 * - 消息正文：与序列化算法有关
 *
 * 继承这个父类就是默认线程安全的
 * @since 2021/6/1 2:33 下午
 */
@ChannelHandler.Sharable
@Slf4j
public class MessageCodecShareable extends MessageToMessageCodec<ByteBuf,Message> {
    //编码
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {

        ByteBuf out = ctx.alloc().buffer();
        //魔数 5字节
        byte[] magicCode = "hpyBc".getBytes(StandardCharsets.UTF_8);
        out.writeBytes(magicCode);
        //版本 1字节
        byte version = 1;
        out.writeByte(version);
        //序列化方式 1字节  0:jdk序列化 1：json序列化
        byte serialize = 0;
        out.writeByte(serialize);
        //指令类型 1字节
        out.writeByte(msg.getMessageType());
        //指令序号 4个字节
        out.writeInt(msg.getSequenceId());
        //准备正文信息
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(msg);
        byte[] bytes = bs.toByteArray();
        //长度 4个字节
        out.writeInt(bytes.length);
        //正文
        out.writeBytes(bytes);

        outList.add(out);
    }

    /**
     * 必须和{@link io.netty.handler.codec.LengthFieldBasedFrameDecoder} 一起使用，确保ByteBuf是完整的
     * @param ctx ctx
     * @param in 传入的ByteBuf
     * @param out 传输到下一个
     * @throws Exception 异常
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1.魔数
        byte[] magicCode = new byte[5];
        in.readBytes(magicCode,0,5);
        //2.版本
        byte version = in.readByte();
        //3.序列化方式
        byte serialize = in.readByte();
        //4.类型
        byte messageType = in.readByte();
        //5.指令序号
        int sequenceId = in.readInt();
        //6.长度
        int length = in.readInt();
        //7.正文
        byte[] content = new byte[length];
        in.readBytes(content,0,length);
        Message message = null;
        if (serialize == 0){
            //jdk
            ByteArrayInputStream bi = new ByteArrayInputStream(content);
            ObjectInputStream is = new ObjectInputStream(bi);
            message =  (Message) is.readObject();
        }
        //传递给下一个handler
        out.add(message);
        log.debug("{},{},{},{},{},{},{}",magicCode,version,serialize,messageType,sequenceId,length,message);
    }
}
