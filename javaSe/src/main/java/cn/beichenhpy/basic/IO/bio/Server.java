package cn.beichenhpy.basic.IO.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Server description：
 * @since 2021/5/15 8:04 下午
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("服务器已经启动。。正在监听9999端口。。。");
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            while (true) {
                //这里会阻塞while 监听9999 直到收到客户端消息才会释放
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ServerThread implements Runnable {

        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //获取输入流读取客户端信息
            try (InputStream inputStream = socket.getInputStream()) {
                //按行读取
                byte[] buff = new byte[1024];
                int len;
                while ((len = inputStream.read(buff)) > 0) {
                    System.out.println(Thread.currentThread().getName() + "收到消息：" + new String(buff, 0, len));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}
