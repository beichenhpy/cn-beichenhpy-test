package cn.beichenhpy.basic.IO.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote SelectorTest description：selector多路复用选择器
 * @since 2021/5/25 6:15 下午
 */
public class SelectorTest {
    static class Server {
        public static void main(String[] args) {
            //打开服务端channel
            try (ServerSocketChannel ssChannel = ServerSocketChannel.open()) {
                //非阻塞
                ssChannel.configureBlocking(false);
                //绑定端口
                ssChannel.bind(new InetSocketAddress(9999));
                //设置selector
                try (Selector selector = Selector.open()) {
                    //注册
                    ssChannel.register(selector, SelectionKey.OP_ACCEPT);
                    //轮询
                    System.out.println("注册完成，开始等待");
                    while (selector.select() > 0) {
                        System.out.println("轮询一次");
                        //获取所有事件
                        final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()){
                            SelectionKey sk = iterator.next();
                            //如果是接入，则注册
                            if (sk.isAcceptable()) {
                                final SocketChannel channel = ssChannel.accept();
                                channel.configureBlocking(false);
                                channel.register(selector, SelectionKey.OP_READ);
                            } else if (sk.isReadable()) {
                                //获取当前选择器上"读就绪"的事件
                                SocketChannel channel = (SocketChannel) sk.channel();
                                //读取数据
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int len = 0;
                                while ((len = channel.read(buffer)) > 0) {
                                    buffer.flip();
                                    System.out.println(new String(buffer.array(), 0, len));
                                    buffer.clear();
                                }
                            }
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    static class Client {
        public static void main(String[] args) {
            try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9999))) {
                socketChannel.configureBlocking(false);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("请输入：");
                    String str = scanner.nextLine();
                    str = str + System.currentTimeMillis();
                    buffer.put(str.getBytes(StandardCharsets.UTF_8));
                    //转为读模式
                    buffer.flip();
                    socketChannel.write(buffer);
                    //清空缓冲区
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
