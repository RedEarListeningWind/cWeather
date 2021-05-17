package com.crtf.weather.util.executors;

import java.util.concurrent.Future;
import java.util.function.Consumer;

import lombok.Data;

/**
 * @author crtf
 * @date 2021年5月15日 星期六 0:40
 * @version 1.0
 * @param <T>
 */
@Data
public class FutureAndConsumer<T> {

    private Future<T> future;
    private Consumer<Future<T>> consumer;

    /**
     * 如果此任务完成，则返回true 。
     * 完成可能是由于正常终止，异常或取消引起的，在所有这些情况下，此方法都将返回true 。
     * @return
     */
    public final boolean isDone(){
        return future.isDone();
    }

    /**
     * 如果此任务在正常完成之前被取消，则返回true 。
     * @return
     */
    public final boolean isCancelled(){
        return future.isCancelled();
    }


}
