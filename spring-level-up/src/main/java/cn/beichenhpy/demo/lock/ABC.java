package cn.beichenhpy.demo.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ABC {
    static int now = 0;
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition_a = lock.newCondition();
    static Condition condition_b = lock.newCondition();
    static Condition condition_c = lock.newCondition();

    private void print_a() throws InterruptedException {
        lock.lock();
        while (now != 0){
            condition_a.await();
        }
        System.out.print("A");
        now ++;
        condition_b.signal();
        lock.unlock();
    }
    private void print_b() throws InterruptedException {
        lock.lock();
        while (now != 1){
            condition_b.await();
        }
        System.out.print("B");
        now ++;
        condition_c.signal();
        lock.unlock();
    }
    private void print_c() throws InterruptedException {
        lock.lock();
        while (now != 2){
            condition_a.await();
        }
        System.out.print("C ");
        now = 0;
        condition_a.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        ABC abc = new ABC();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    abc.print_a();
                    abc.print_b();
                    abc.print_c();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
