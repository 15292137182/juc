package cn.wentiehu.juc;

/**
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2018/12/23 21:53
 */
public class VolatileTest {
    public static void main(String[] args) {

                /*
        问题：
        main线程顺序执行，
                    testThread新启动一个线程，启动一个线程会在内存中单独的开辟一个空间，
                    在子线程中睡200毫秒，然后再去修改isFlag的值，修改完的值会单独的储存在开辟的空间中，（并不会同步到主内存中去）
                    此时main线程会一直执行，子线程修改的值不会共享在主内存中
         */

        TestThread testThread = new TestThread();
        new Thread(testThread).start();

        while (true) {
            /*
            fixme 方案一：在while 循环体重添加 synchronized 关键字，synchronized 具有资源共享的作用，但其效率慢
             */
//            synchronized (testThread) {
                if (testThread.isFlag()) {
                    System.out.println("main 线程");
                    break;
                }
//            }
        }

    }
}


class TestThread implements Runnable {
    /*
    fixme 方案二： 添加 volatile 关键字，volatile 具备可见性，无法保证原子性。当多个线程访问同一个变量时，一个线程修改变量的值，其他的线程能共享变量修改后的值
     */
    private volatile boolean flag = false;


    @Override
    public void run() {
        try {
            // 该线程 sleep(200), 导致了程序无法执行成功
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("线程1 flag=" + isFlag());
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
