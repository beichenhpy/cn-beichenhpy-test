package cn.beichenhpy.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote ByteBufTest description：存储的都是16进制的位数 8位
 * <br> 与 ByteBuffer相比的优势
 * <br> 1.有池化功能 在 4.1以后，能够服用ByteBuf，提交了IO
 * <br> 2.不用切换模式，ByteBuffer|写时需要clear/compact|读时需要flip,ByteBuf使用读写指针，自动分配区域，不用切换模式
 * <br> 3.自动扩容
 * <br> 4.零拷贝 slice duplicate compositeByteBuf Unpooled类
 * <br> 区域：废弃区 ----  可读区 ----  写入区 ----  扩容区 |||读取过就变为废弃区
 * @since 2021/5/27 1:51 下午
 */
@Slf4j
public class ByteBufTest {

    public static void BufLog(ByteBuf byteBuf){
        int len = byteBuf.readableBytes();
        int rows = len / 16 + (len % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(byteBuf.readerIndex())
                .append(" write index:").append(byteBuf.writerIndex())
                .append(" capacity:").append(byteBuf.capacity())
                .append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(buf,byteBuf);
        System.out.println(buf);
    }
    @Test
    public void AutoCapacity(){
        //ByteBuf可以自动扩容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        BufLog(buffer);
        //扩容测试
        for (int i = 0; i < 300; i++) {
            buffer.writeBytes("a".getBytes(StandardCharsets.UTF_8));
        }
        BufLog(buffer);
    }

    /**
     * 也分为直接内存和堆内存
     * <br> 默认使用直接内存
     * <br> 扩容规则：
     * <br> 1.如果写入后数据大小未超过512，则扩容后的capacity为下一个16的整数倍
     * <br> 2.如果写入的数据大小超过512，则选择下一个2^n
     * <br> 3.扩容不能大于maxCapacity
     */
    @Test
    public void memory(){
        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();
        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();
        ByteBuf defaultBuffer = ByteBufAllocator.DEFAULT.buffer();
        log.debug("类信息：{}",heapBuffer.getClass());
        log.debug("类信息：{}",directBuffer.getClass());
        log.debug("类信息：{}",defaultBuffer.getClass());
    }

    /**
     * 写入相关方法 会改变指针位置
     * <br> writeXXX(XXX value)
     * <br> {@link ByteBuf#writeBoolean(boolean)} 用一个字节 01|00 代表 true|false
     * <br> {@link ByteBuf#writeInt(int)} 大端写入 即写入 0x250 -> 00 00 02 50
     * <br> {@link ByteBuf#writeIntLE(int)} 小端写入 即写入 0x250 -> 50 02 00 00
     * <br> {@link ByteBuf#writeCharSequence(CharSequence, Charset)} 写入字符串-String/StringBuilder/StringBuffer
     * <br> 还有以set开头的方法，也能写入数据，但是不改变指针位置
     */
    @Test
    public void writeMethod(){
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(new byte[]{1,2,3,4});
        BufLog(buf);
        //转为16进制存储
        buf.writeInt(0x64);
        BufLog(buf);
        buf.writeBoolean(false);
        BufLog(buf);
        buf.writeCharSequence("hello",Charset.defaultCharset());
        BufLog(buf);
        //set方法。相当于是替换某个index上的值
        buf.setByte(0,2);
        BufLog(buf);
    }

    /**
     * 读取相关方法
     * readXXX();
     * 重复读取类似于{@link java.nio.ByteBuffer#mark()} 和 {@link ByteBuffer#reset()}
     * api:{@link ByteBuf#markReaderIndex()} 和 {@link ByteBuf#resetReaderIndex()}
     */
    @Test
    public void readMethod(){
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeCharSequence("hello_world",Charset.defaultCharset());
        System.out.println(((char) buf.readByte()));
        BufLog(buf);
        //重复读取
        buf.markReaderIndex();
        System.out.println(((char) buf.readByte()));
        BufLog(buf);
        buf.resetReaderIndex();
        System.out.println(((char) buf.readByte()));
        BufLog(buf);
    }

    /**
     * 内存回收♻️️
     * <br> 不同种类的ByteBuf回收也不同
     * <br> 一、未池化
     * <br>   -- 堆内存 使用的是JVM的内存，等待垃圾回收
     * <br>   -- 直接内存 使用特殊方法回收
     * <br> 二、池化
     * <br>   -- 所有都要以特殊的方式回归池
     * <br> 所有的回收都是用的是
     * <br> {@link AbstractReferenceCounted#deallocate()}
     * <br> netty 这里采用了引用计数法控制回收内存，每个ByteBuf都实现了ReferenceCounted 接口
     * <br>   -- 每个ByteBuf对象的初始计数器为1
     * <br>   -- 每个release方法计数减一，如果计数为0，ByteBuf内存被回收
     * <br>   -- 调用retain方法计数加一,表示调用者没用完前，其他handler调用了release也不会回收
     * <br>   -- 当计数为0时，底层内存会被回收，这时即使ByteBuf对象还在，其各个方法无法正常使用
     * @see ReferenceCounted#release() 释放回收
     * @see ReferenceCounted#retain()
     */
    @Test
    public void retainAndRelease(){

    }

    /**
     * 切片
     * 零拷贝的一种实现
     * 对原始的ByteBuf进行切片成多个ByteBuf,切片的ByteBuf并没有发生内存复制，还是使用的原始内存，切片后的ByteBuf维护独立的read/write指针
     * 优点：避免了大量的内存交换
     * 注意：会对切片后的ByteBuf的capacity做限制，不会自增
     */
    @Test
    public void slice(){
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        BufLog(buf);
        //从index开始切5个
        ByteBuf b1 = buf.slice(0, 5);
        //防止buf调用release释放，要用retain自己管理自己的内存
        b1.retain();
        ByteBuf b2 = buf.slice(5, 5);
        b2.retain();
        BufLog(buf);
        BufLog(b1);
        BufLog(b2);
        b1.setByte(0,'z');
        BufLog(buf);
        BufLog(b1);
        //read不影响原ByteBuf
        b2.readByte();
        BufLog(buf);
        BufLog(b1);
        BufLog(b2);
        //释放已读空间 让readIndex = 0
        b1.discardReadBytes();
        BufLog(buf);
        BufLog(b1);
        //这里不能write 因为容量有限不能自增 因为此时write index = 5了
//        b2.writeByte('k');
//        BufLog(buf);
//        BufLog(b1);
//        BufLog(b2);
        //释放
        b1.release();
        b2.release();
        buf.release();
    }

    /**
     * 零拷贝体现之一
     * 将原ByteBuf复制，并且没有最大长度限制
     */
    @Test
    public void duplicate(){
        ByteBuf origin = ByteBufAllocator.DEFAULT.buffer();
        origin.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        ByteBuf duplicate = origin.duplicate();
        //写入不影响原来的ByteBuf
        duplicate.writeByte('z');
        BufLog(origin);
        BufLog(duplicate);
        //set会影响原来的ByteBuf
        duplicate.setByte(1,'d');
        BufLog(origin);
        BufLog(duplicate);
        //release不影响
        origin.release();
    }

    /**
     * 零拷贝组合
     * 共享计数器，所以记得要用retain
     */
    @Test
    public void Composite(){
        ByteBuf part1 = ByteBufAllocator.DEFAULT.buffer();
        part1.writeBytes(new byte[]{1,2,3,4,5});
        ByteBuf part2 = ByteBufAllocator.DEFAULT.buffer();
        part2.writeBytes(new byte[]{6,7,8,9,0});

        CompositeByteBuf all = ByteBufAllocator.DEFAULT.compositeBuffer();
        all.addComponents(true,part1,part2);
        all.retain();
        BufLog(all);
    }

}
