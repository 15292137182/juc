package cn.wentiehu.juc;

import java.util.concurrent.*;

/**
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外的开销，提高了效应速度
 * <p>
 * 二、线程池的体系结构：
 * &nbsp;java.util.concurrent.Executor: 负责线程的使用和调度的根接口;
 * &nbsp;&nbsp;|---ExecutorService: 子接口,线程池的主要接口;
 * &nbsp;&nbsp;&nbsp;|---ThreadPoolExecutor: 线程池的实现类;
 * &nbsp;&nbsp;&nbsp;|---ScheduledExecutorService: 子接口,负责线程的调度;
 * &nbsp;&nbsp;&nbsp;&nbsp;|---ScheduledThreadPoolExecutor: 继承了线程池的实现类,实现了负责线程调度的子接口;
 * <p>
 * 三、工具类: Executors
 * &nbsp;&nbsp;ExecutorService newFixedThreadPool(): 创建固定大小的线程池;
 * &nbsp;&nbsp;ExecutorService newCachedThreadPool(): 缓存线程池,线程池中线程的数量不固定,可以根据需求自动更改数量;
 * &nbsp;&nbsp;ExecutorService newSingleThreadExecutor(): 创建单个线程池, 线程池中只有一个线程;
 * &nbsp;&nbsp;ScheduledExecutorService newScheduledThreadPool(): 创建固定大小的线程,可以延时或定时的执行任务;
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/13 14:29
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
//        1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

//        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
//
////        2.为线程池中的线程分配任务
////        pool.submit(threadPoolDemo);
//
//        for (int i = 0; i < 5; i++) {
//            pool.submit(threadPoolDemo);
//        }
//
////        3.关闭线程池
//        pool.shutdown();


        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                int num = 0;
                for (int i = 0; i <= 100; i++) {
                    num += i;
                }
                return num;
            }
        });

        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //关闭线程池
        pool.shutdown();


    }
}

class ThreadPoolDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
