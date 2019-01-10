package cn.wentiehu.juc;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分段锁
 * ConcurrentHashMap 同步容器类是 Java5 增加的一个线程安全的哈希表;介于 HashMap 与 Hashtable 之间;
 * 内部采用"锁分段"机制替代Hashtable的独占锁,进而提高性能;
 * <p>
 * 此包还提供了设计用于多线程上下文中的Collection实现:
 * <p>
 * ---ConcurrentHashMap,
 * ---ConcurrentSkipListMap,
 * ---ConcurrentSkipListSet,
 * ---CopyOnWriteArrayList,
 * ---CopyOnWriteArraySet;
 * <p>
 * 当期望许多线程访问一个给定collection时,
 * ConcurrentHashMap通常优于同步的HashMap;
 * ConcurrentSkipListMap通常优于同步的TreeMap;
 * 当期望的读数和遍历远远大于列表的更新数时,
 * CopyOnWriteArrayList优于同步的ArrayList;
 *
 * @author wentiehu
 * @email tiehuwen@163.com
 * @date 2019/1/2 17:54
 */
//public class ConcurrentHashMapTest {
//
//    public static void main(String[] args) {
//
//        System.out.println();
//        Map map = new HashMap();
//        new Thread(() -> {
//            for (int i = 0; i < Integer.MAX_VALUE; i++) {
//                new Thread(() -> {
//                    map.put((int) (1 + Math.random() * 100), (int) (1 + Math.random() * 100));
//                }, "子线程" + i).start();
//            }
//        }, "线程1").start();
//    }
//
//}
public class ConcurrentHashMapTest extends Thread {
    /**
     * 类的静态变量是各个实例共享的，因此并发的执行此线程一直在操作这两个变量
     * 选择AtomicInteger避免可能的int++并发问题
     */
    private static AtomicInteger ai = new AtomicInteger(0);
    //初始化一个table长度为1的哈希表
    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(1);
    //如果使用ConcurrentHashMap，不会出现类似的问题
//       private static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>(1);

    public void run() {
        while (ai.get() < 100000000) {  //不断自增
            map.put(ai.get(), ai.get());
            ai.incrementAndGet();
        }

        System.out.println(Thread.currentThread().getName() + "线程即将结束");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new ConcurrentHashMapTest().start();
        }

    }
}