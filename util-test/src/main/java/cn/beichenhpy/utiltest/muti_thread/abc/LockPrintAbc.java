package cn.beichenhpy.utiltest.muti_thread.abc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote LockPrintAbc description：
 * @since 2021/5/17 1:43 下午
 */
public class LockPrintAbc {

    ReentrantLock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();
    AtomicInteger current = new AtomicInteger(0);

    public void printA() {
        lock.lock();
        try {
            if (current.get() != 0) {
                conditionA.await();
            }
            System.out.println("A");
            current.compareAndSet(0,1);
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            if (current.get() != 1) {
                conditionB.await();
            }
            System.out.println("B");
            current.compareAndSet(1,2);
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            if (current.get() != 2) {
                conditionC.await();
            }
            System.out.println("C");
            current.compareAndSet(2,0);
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final LockPrintAbc lockPrintAbc = new LockPrintAbc();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lockPrintAbc.printA();
            }
                }
        ).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lockPrintAbc.printB();
            }
        }
        ).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lockPrintAbc.printC();
            }
        }
        ).start();
    }
}
