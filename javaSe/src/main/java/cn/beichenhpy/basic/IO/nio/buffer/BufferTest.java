package cn.beichenhpy.basic.IO.nio.buffer;

import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote BufferTest description：对Buffer常用api实现
 * @since 2021/5/25 3:54 下午
 */
public class BufferTest {

    public void printInfo(Buffer buffer) {
        System.out.println("初始容量：" + buffer.capacity());
        System.out.println("起始位置：" + buffer.position());
        System.out.println("限制位置：" + buffer.limit());
        System.out.println("起始和限制之间的元素个数：" + buffer.remaining());
        System.out.println("-----------------------------------");
    }

    /**
     * 写入读取测试
     */
    @Test
    public void test01() {
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printInfo(buffer);
        //向缓冲区添加数据
        String name = "hpy";
        buffer.put(name.getBytes(StandardCharsets.UTF_8));
        printInfo(buffer);
        //转为读取模式 将position = 0,limit = remaining数
        buffer.flip();
        printInfo(buffer);
        //get读取数据
        char c = (char) buffer.get();
        System.out.println(c);
        printInfo(buffer);
    }

    /**
     * 缓冲区清空测试
     */
    @Test
    public void test02() {
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printInfo(buffer);
        //添加数据
        String name = "hpy";
        buffer.put(name.getBytes(StandardCharsets.UTF_8));
        printInfo(buffer);
        //清空缓冲区-->position、limit恢复初始值
        buffer.clear();
        printInfo(buffer);
        //get读取数据 可以get到数据，说明只是position、limit恢复初始值，然而数据还是存在的等待覆盖
        char c = (char) buffer.get();
        System.out.println(c);
    }

    /**
     * get(byte[] bytes)
     * mark
     * reset
     * 测试
     */
    @Test
    public void test03() {
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printInfo(buffer);
        //添加数据
        String name = "hpyyyds";
        buffer.put(name.getBytes(StandardCharsets.UTF_8));
        printInfo(buffer);
        //读取模式
        buffer.flip();
        //get() 获取一个
        System.out.println("get获取一个：" + (char) buffer.get());
        //mark标记position 用于reset
        buffer.mark();
        printInfo(buffer);
        //get(byte[] bytes)
        byte[] buff = new byte[6];
        buffer.get(buff);
        System.out.println("获取剩余的6个：" + new String(buff));
        printInfo(buffer);
        //reset
        buffer.reset();
        printInfo(buffer);
    }

    /**
     * 直接内存-非堆内存
     * 使用场景：1、数据量大的操作，生命周期很长
     *         2、频繁的IO、网络并发场景
     */
    @Test
    public void test04() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println("是否是直接缓冲区：" + buffer.isDirect());
    }
}
