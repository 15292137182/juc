package cn.wentiehu.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/13 16:41
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) {
//        Instant start = Instant.now();
//        ForkJoinPool pool = new ForkJoinPool();
//
//        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0, 10000000000L);
//
//        Long result = pool.invoke(task);
//
//        System.out.println(result);
//
//        Instant end = Instant.now();
//        System.out.println("耗时时间为：" + Duration.between(start, end).toMillis());

        test1();
    }


    public static void test() {
        Instant start = Instant.now();

        long sum = 0L;

        for (long i = 0; i < 10000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时时间为：" + Duration.between(start, end).toMillis());
    }



    //java 8 新特性
    public static void test1() {
        Instant start = Instant.now();
        Long sum = LongStream.rangeClosed(0L, 10000000000L).parallel().reduce(0L, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时时间为：" + Duration.between(start, end).toMillis());
    }

}

class ForkJoinSumCalculate extends RecursiveTask<Long> {


    private static final long serialVersionUID = -5312220843012382472L;

    private long start;
    private long end;

    public static final long THURSHOLD = 10000L; //临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();//进行拆分，同时压人线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}