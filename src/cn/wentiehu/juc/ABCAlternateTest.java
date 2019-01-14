package cn.wentiehu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这三个线程的ID分别是A、B、C，每个线程
 * 将自己的ID在屏幕上打印10遍，要求输出的结果必须是按顺序显示。
 * 如：ABCABCABC.....依次递归
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/13 13:26
 */
public class ABCAlternateTest {
    public static void main(String[] args) {
        AlternateDemo alternateDemo = new AlternateDemo();
        new Thread(() -> {
            for (int i = 0; i <= 20; i++) {
                alternateDemo.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i <= 20; i++) {
                alternateDemo.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i <= 20; i++) {
                alternateDemo.loopC(i);
                System.out.println("-----------------------------");
            }
        }, "C").start();
    }

}

class AlternateDemo {
    private int num = 1;//表示当前正在执行线程的标记

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    /**
     * @param totalLoop 循环第几轮
     */
    public void loopA(int totalLoop) {
        lock.lock();
        try {
//      1 .判断
            if (num != 1) {
                condition1.await();
            }
//      2. 打印

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + "\t" + i + "\t" + totalLoop);
            }

//      3.唤醒
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param totalLoop 循环第几轮
     */
    public void loopB(int totalLoop) {
        lock.lock();
        try {
//      1 .判断
            if (num != 2) {
                condition2.await();
            }
//      2. 打印

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + "\t" + i + "\t" + totalLoop);
            }

//      3.唤醒
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param totalLoop 循环第几轮
     */
    public void loopC(int totalLoop) {
        lock.lock();
        try {
//      1 .判断
            if (num != 3) {
                condition3.await();
            }
//      2. 打印

            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + "\t" + i + "\t" + totalLoop);
            }

//      3.唤醒
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
