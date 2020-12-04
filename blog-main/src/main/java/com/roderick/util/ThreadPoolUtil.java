package com.roderick.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ThreadPoolUtil {

    private ExecutorService threadPool = new ThreadPoolExecutor(
            1,  //线程池中最小的线程数
            5,
            0L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10),  //阻塞队列  可以处理的最大请求：max + deque
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());  //请求满了会抛弃请求，并抛出异常

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }
}
