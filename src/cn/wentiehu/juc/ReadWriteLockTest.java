package cn.wentiehu.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLock:读写锁
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/13 13:53
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        new Thread(() -> {
            readWriteLockDemo.set((int) (Math.random() * 101));
        }, "write:").start();
        for (int i = 0; i < 100; i++) {
            new Thread(readWriteLockDemo::get).start();
        }
    }
}

class ReadWriteLockDemo {
    private int num = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();


    //读
    public void get() {

        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + num);
        } finally {
            lock.readLock().unlock();
        }

    }

    //写
    public void set(int num) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
