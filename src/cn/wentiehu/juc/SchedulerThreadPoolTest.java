package cn.wentiehu.juc;

import java.util.Random;
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
public class SchedulerThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
            ScheduledFuture<Integer> future = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Random random = new Random();
                    int num = random.nextInt(100);
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
                //每隔3秒执行
            }, 3, TimeUnit.SECONDS);

            Integer result = null;
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
        pool.shutdown();
    }
}
