package cn.beichenhpy.basic.bio;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Client description：
 * @since 2021/5/15 8:11 下午
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //客户端连接
            try (final Socket socket = new Socket("127.0.0.1", 9999);) {
                new Thread(new ClientThread(socket, i)).start();
                //需要睡100ms,因为太快会提前关闭socket 导致服务端未收到消息
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientThread implements Runnable {

        private Socket socket;
        private int time;

        public ClientThread(Socket socket, int time) {
            this.socket = socket;
            this.time = time;
        }

        @Override
        public void run() {
            try(final OutputStream outputStream = socket.getOutputStream()) {
                System.out.println("发送消息" + time);
                //获取输出流
                outputStream.write(("测试" + time).getBytes(StandardCharsets.UTF_8));
            } catch (Exception ee) {
                ee.printStackTrace();
            }


        }
    }
}
