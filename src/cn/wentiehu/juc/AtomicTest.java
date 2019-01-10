package cn.wentiehu.juc;


import java.util.concurrent.atomic.AtomicInteger;

import static cn.wentiehu.juc.AtomicDemo.threadSecurity;
import static cn.wentiehu.juc.AtomicDemo1.threadSecurity1;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/2 15:48
 */
public class AtomicTest {

    public static void main(String[] args) {
        /*
        1、i++的操作实际上分为三个步骤: "读-改-写";
        2、原子性: 就是"i++"的"读-改-写"是不可分割的三个步骤;
        3、原子变量: JDK1.5 以后, java.util.concurrent.atomic包下,提供了常用的原子变量;
            原子变量中的值,使用 volatile 修饰,保证了内存可见性;
            CAS(Compare-And-Swap) 算法保证数据的原子性;
         */
//        int i = 10;
////        i  = i++;
//
//        int temp = i;
//        i = i + 1;
//        i = temp;
//        System.out.println(i);


//        存在线程安全问题
//        threadSecurity();

//       使用原子变量
        threadSecurity1();
    }


}

/**
 * 多线程操作数据，存在线程安全
 */
class AtomicDemo implements Runnable {
    private int serialNumber = 0;


    public static void threadSecurity() {
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i <= 10; i++) {
            new Thread(atomicDemo).start();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":  " + getSerialNumber());


            /*
            多个线程修改变量时，存在线程安全问题
Thread-10:  0
Thread-6:  2
Thread-7:  0
Thread-2:  4
Thread-8:  6
Thread-4:  0
Thread-5:  0
Thread-3:  5
Thread-1:  1
Thread-0:  3
Thread-9:  7
             */
    }

    int getSerialNumber() {
        return serialNumber++;
    }
}


/**
 * 改进: 使用原子变量
 */
class AtomicDemo1 implements Runnable {

    public static void threadSecurity1() {
        AtomicDemo1 atomicDemo = new AtomicDemo1();
        for (int i = 0; i <= 10; i++) {
            new Thread(atomicDemo).start();
        }
    }

    private AtomicInteger serialNumber = new AtomicInteger();

    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        // 自增运算
        return serialNumber.getAndIncrement();
    }
}