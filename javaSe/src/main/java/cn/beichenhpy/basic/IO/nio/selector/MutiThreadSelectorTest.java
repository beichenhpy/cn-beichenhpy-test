package cn.beichenhpy.basic.IO.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote MutiThreadSelectorTest description：多线程nio模型
 * boss线程只用来处理accept 事件
 * 开启新的worker线程处理 read等事件
 * @since 2021/5/26 1:25 下午
 */
@Slf4j
public class MutiThreadSelectorTest {
    public static void main(String[] args) throws IOException {
        //客户端boss负责连接 accept
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(9999));
        ssChannel.configureBlocking(false);
        Selector boss = Selector.open();
        ssChannel.register(boss, SelectionKey.OP_ACCEPT);
        //注册workerGroup
        Worker[] workerGroup = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workerGroup.length; i++) {
            workerGroup[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger();
        while (true) {
            //阻塞
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    SocketChannel acceptChannel = ssChannel.accept();
                    acceptChannel.configureBlocking(false);
                    log.debug("before --{}",acceptChannel.getRemoteAddress());
                    workerGroup[index.getAndIncrement() % workerGroup.length].ready(acceptChannel);
                    log.debug("after --{}",acceptChannel.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable{
        //worker对应线程
        private Thread thread;
        //对应selector
        private Selector workerSelector;
        //worker-i 名字
        private String name;
        //是否开始线程
        private volatile boolean start = false;
        //队列 负责线程通信
        private ConcurrentLinkedQueue<Runnable> workerQueue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        public void ready(SocketChannel socketChannel) throws IOException {
            //初始化线程、Selector
            if (!start){
                thread = new Thread(this,name);
                workerSelector = Selector.open();
                thread.start();
            }
            //将注册任务添加到队列中 boss线程调用添加
            workerQueue.add(()->{
                try {
                    socketChannel.register(workerSelector,SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            //唤醒Selector
            workerSelector.wakeup();
        }

        @Override
        public void run() {
            while (true){
                try {
                    //worker 线程
                    workerSelector.select();
                    Runnable task = workerQueue.poll();
                    //worker线程
                    if (task != null){
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = workerSelector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey sk = iterator.next();
                        iterator.remove();
                        //处理读事件
                        if (sk.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(32);
                            //获取channel通道 异常自动关闭
                            try (SocketChannel channel = (SocketChannel) sk.channel()){
                                log.debug("read---{}",channel.getRemoteAddress());
                                int len = 0;
                                while ((len = channel.read(buffer)) > 0) {
                                    buffer.flip();
                                    log.debug(new String(buffer.array(), 0, len));
                                    buffer.clear();
                                }
                                //当客户端关闭时，仍然有消息可读 只是为 -1 要手动关闭 channel
                                if (len == -1){
                                    channel.close();
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
