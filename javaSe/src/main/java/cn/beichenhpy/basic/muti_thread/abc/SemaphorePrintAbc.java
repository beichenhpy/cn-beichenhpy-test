package cn.beichenhpy.basic.muti_thread.abc;


import java.util.concurrent.Semaphore;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote SemaphorePrintAbc description：利用信号量打印ABC
 * @since 2021/5/17 12:14 下午
 */
public class SemaphorePrintAbc {
    private Semaphore a_semaphore = new Semaphore(1);
    private Semaphore b_semaphore = new Semaphore(1);
    private Semaphore c_semaphore = new Semaphore(1);
    class printA implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    a_semaphore.acquire();
                    System.out.println("A");
                    b_semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    class printB implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    b_semaphore.acquire();
                    System.out.println("B");
                    c_semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    class printC implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    c_semaphore.acquire();
                    System.out.println("C");
                    a_semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final SemaphorePrintAbc semaphorePrintAbc = new SemaphorePrintAbc();
        //先阻塞BC
        try {
            semaphorePrintAbc.b_semaphore.acquire();
            semaphorePrintAbc.c_semaphore.acquire();
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(semaphorePrintAbc.new printA()).start();
        new Thread(semaphorePrintAbc.new printB()).start();
        new Thread(semaphorePrintAbc.new printC()).start();
    }
}
