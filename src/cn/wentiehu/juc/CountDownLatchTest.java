package cn.wentiehu.juc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/10 20:42
 */
public class CountDownLatchTest {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchTest latchTest = new LatchTest(countDownLatch);
        for (int i = 0; i < 5; i++) {
            new Thread(latchTest).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时为" + (end - start) + "毫秒");
    }

}

class LatchTest implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchTest(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        synchronized (this) {
            List list = new LinkedList();
//            CopyOnWriteArrayList list = new CopyOnWriteArrayList();
            try {
                for (int i = 0; i < 10000; i++) {
                    if (i % 2 == 0) {
                        list.add(i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(list);
                countDownLatch.countDown();
            }
        }
    }
}
