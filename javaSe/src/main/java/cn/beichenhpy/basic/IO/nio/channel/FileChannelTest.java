package cn.beichenhpy.basic.IO.nio.channel;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote FileChannelTest description：使用byteBuffer + FileChannel实现文件写入
 * @since 2021/5/25 4:56 下午
 */
public class FileChannelTest {
    @Test
    public void writeToTxt() {
        //字节输出流
        try (FileOutputStream os = new FileOutputStream("data.txt")) {
            //获取Channel通道
            try (FileChannel channel = os.getChannel()) {
                //声明Buffer
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //写入数据
                buffer.put("hpy测试永远滴神".getBytes(StandardCharsets.UTF_8));
                //改为读模式
                buffer.flip();
                //channel写入
                channel.write(buffer);
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readFromTxt() {
        try (FileInputStream inputStream = new FileInputStream("data.txt")) {
            try (FileChannel channel = inputStream.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                buffer.flip();
                byte[] array = buffer.array();
                System.out.println(new String(array, buffer.position(), buffer.remaining()));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copy() {
        File sourFile = new File("data.txt");
        File descFile = new File("data_cp.txt");
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try (final FileInputStream inputStream = new FileInputStream(sourFile)) {
            try (final FileChannel sourceChannel = inputStream.getChannel()) {
                while (true) {
                    //每次重置到开头
                    buffer.clear();
                    //输入流读取数据到buffer
                    int flag = sourceChannel.read(buffer);
                    //无数据退出循环
                    if (flag == -1) {
                        break;
                    }
                    try (final FileOutputStream outputStream = new FileOutputStream(descFile)) {
                        try (final FileChannel outChannel = outputStream.getChannel()) {
                            //转为读取模式
                            buffer.flip();
                            //写入
                            outChannel.write(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("复制完成");
    }

    /**
     * 分散和聚集
     * 分散：把Channel通道中的数据写入到多个缓冲区
     * 聚集：把多个Buffer中的数据，聚集到Channel中
     */
    @Test
    public void scatterAndGather() {
        ByteBuffer buffer1 = ByteBuffer.allocate(9);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = {buffer1, buffer2};
        //分散
        try (FileInputStream inputStream = new FileInputStream("data.txt")) {
            try (FileChannel inChannel = inputStream.getChannel()) {
                //读到缓冲区
                inChannel.read(buffers);
                //查询是否读入
                for (ByteBuffer buffer : buffers) {
                    //切换到读模式
                    buffer.flip();
                    System.out.println(new String(buffer.array(), buffer.position(), buffer.remaining()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //聚集
        try (FileOutputStream outputStream = new FileOutputStream("data_gather.txt")) {
            try (FileChannel outChannel = outputStream.getChannel()) {
                //这里不用buffer.flip()是因为在上面 分散 的时候 已经进行过了，并且没有put/get操作
                outChannel.write(buffers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("聚集完成");
        }
    }

    /**
     * 针对于transformTo/transformFrom 简便赋值操作
     */
    @Test
    public void transform() {
        try (FileInputStream inputStream = new FileInputStream("data.txt")) {
            try (FileChannel inChannel = inputStream.getChannel()) {
                try (FileOutputStream outputStream = new FileOutputStream("data_transform.txt")) {
                    try (FileChannel outChannel = outputStream.getChannel()) {
                        //任选其一
                        //transformFrom
//                        outChannel.transferFrom(inChannel, inChannel.position(), inChannel.size());
                        //transformTo
                        inChannel.transferTo(inChannel.position(), inChannel.size(), outChannel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("复制完成");
        }
    }
}
