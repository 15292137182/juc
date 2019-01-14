package cn.wentiehu.juc;

/**
 * 题目：判断打印的 “one” or “two” ？
 * 1.两个普通的同步方法，两个线程，标准打印,打印?// one two
 * 2.新增Thread.sleep()给getOne(), 打印? // one two
 * 3.新增普通方法getThree() , 打印? // three one two
 * 4.两个普通同步方法，两个Number对象，打印? //two one
 * 5.修改getOne为静态同步方法，打印?  //two one
 * 6.修改两个方法均为静态同步方法，一个NUmber对象，打印? // one two
 * 7.一个为静态同步方法，一个非静态同步方法，两个Number对象 打印? //two one
 * 8.两个静态同步方法，两个Number对象 打印? //one two
 * <p>
 * <p>
 * 线程八所得关键：
 * 1.非静态方法的锁默认为：this ，静态方法的锁为对应的Class实例
 * 2.某一时刻内，只能有一个线程持有锁，无论有几个方法。
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/13 14:03
 */
public class Thread8MonitorTest {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(() -> {
            number.getOne();
        }).start();
        new Thread(() -> {
//            number.getTwo();
            number2.getTwo();
        }).start();

//        new Thread(() -> {
//            number.getThree();
//        }).start();
    }
}

//第一锁
//class Number {
//    public synchronized void getOne() {
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//}


//第二锁
//class Number {
//    public synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//}


//第三锁
//class Number {
//    public synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//
//
//    public void getThree() {
//        System.out.println("three");
//    }

//第四锁
//class Number {
//    public synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//
//
//    public void getThree() {
//        System.out.println("three");
//    }
//
//}

//第五锁
//class Number {
//    public static synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//
//
//    public void getThree() {
//        System.out.println("three");
//    }
//}


//第六锁
//class Number {
//    public static synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public static synchronized void getTwo() {
//        System.out.println("two");
//    }
//
//
//    public void getThree() {
//        System.out.println("three");
//    }
//}


//第七锁
//class Number {
//    public static synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("one");
//    }
//
//    public synchronized void getTwo() {
//        System.out.println("two");
//    }
//
//
//    public void getThree() {
//        System.out.println("three");
//    }
//}


class Number {
    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }


    public void getThree() {
        System.out.println("three");
    }
}

