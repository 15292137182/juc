package cn.wentiehu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现callable接口
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/10 21:19
 */
public class ThreadTests {
    public static void main(String[] args) {
//        第一种创建线程的方式
//        new Thread(new RunnableTest()).start();


//        第二种创建线程的方式
//        new ThreadTest().start();

//        第三种创建线程的方式
//        执行 Callable 方式,需要 FutureTask 实现类的支持
//        FutureTask 实现类用于接收运算结果, FutureTask 是 Future 接口的实现类

        CallableTest callableTest = new CallableTest();
        FutureTask<Integer> result = new FutureTask<>(callableTest);
        new Thread(result).start();
        // 接收线程运算后的结果
        try {
            // 只有当 Thread 线程执行完成后,才会打印结果;
            // 因此, FutureTask 也可用于闭锁
            Integer sum = result.get();
            System.out.println(sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class RunnableTest implements Runnable {
    @Override
    public void run() {
        System.out.println("第一种创建线程的方式");
    }
}

class ThreadTest extends Thread {
    @Override
    public void run() {
        System.out.println("第二种创建线程的方式");
    }
}

class CallableTest implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("第三种创建线程的方式");
        // 计算 0~100 的和
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}