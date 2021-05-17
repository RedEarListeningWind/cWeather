package com.crtf.weather.util.executors;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @see ThreadPoolExecutor
 * @see ScheduledThreadPoolExecutor
 * @author crtf
 * @version 1.0
 * @date 2021年5月15日 星期六 0:38
 */
public class ThreadPoolExecutorUtil {

    private static final ThreadPoolExecutor pool;
    private static ScheduledThreadPoolExecutor scheduledPool;

    private static final List<FutureAndConsumer> task;

    static {
        pool = new ThreadPoolExecutor(0, 2, 300,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        task = new CopyOnWriteArrayList<>();
    }

    public static <T> void submit(Callable<T> callable,Consumer<Future<T>> consumer) {
        if (scheduledPool == null){
            scheduledPool = new ScheduledThreadPoolExecutor(1);
        }
        final Future<T> future = pool.submit(callable);
        final FutureAndConsumer<T> futureAndConsumer = new FutureAndConsumer<>();
        futureAndConsumer.setFuture(future);
        futureAndConsumer.setConsumer(consumer);
        task.add(futureAndConsumer);

        waitResultsRunAndCallBack();
    }



    /**
     * 等待运行结果并回调
     */
    private static void waitResultsRunAndCallBack(){
        if (scheduledPool != null) {
            scheduledPool.scheduleAtFixedRate(() -> {
                final FutureAndConsumer[] futureAndConsumers = task.toArray(new FutureAndConsumer[0]);
                for (int i = 0;i < futureAndConsumers.length;i++) {
                    if (futureAndConsumers[i].isDone()) {
                        task.remove(futureAndConsumers[i]);
                        futureAndConsumers[i].getConsumer().accept(futureAndConsumers[i].getFuture());
                    }
                }
                if (task.size() == 0) {
                    scheduledPool.shutdown();
                    scheduledPool = null;
                }
            }, 100, 300, TimeUnit.MILLISECONDS);
        }
    }

    public static boolean isShutdown() {
        return pool.isShutdown();
    }

    /**
     * 启动有序关闭，在该关闭中执行先前提交的任务，但不接受任何新任务。 如果已关闭，则调用不会产生任何其他影响。
     * 此方法不等待先前提交的任务完成执行。 使用awaitTermination可以做到这一点。
     */
    public static void shutdown(){
        pool.shutdown();
        scheduledPool.shutdown();
    }

    /**
     * 尝试停止所有正在执行的任务，暂停正在等待的任务的处理，并返回正在等待执行的任务的列表。 从此方法返回后，这些任务将从任务队列中耗尽（删除）。
     * 此方法不等待主动执行的任务终止。 使用awaitTermination可以做到这一点。
     * 除了尽最大努力尝试停止处理正在执行的任务之外，没有任何保证。 此实现通过Thread.interrupt中断任务； 任何无法响应中断的任务都将永远不会终止。
     * @return
     */
    public static List<Runnable> shutdowNow(){
        if (scheduledPool != null) {
            scheduledPool.shutdownNow();
        }
        return pool.shutdownNow();
    }

}
