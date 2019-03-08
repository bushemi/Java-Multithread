package com.da.lect4.threadpools.exception.example1;

import java.util.concurrent.*;


public class ExceptionHandlerExample {

    public static void main(String[] args)  throws InterruptedException {
        //exception handling in ExceptionHandler set for ThreadFactory
        //this method works only for Runnable tasks
        ThreadFactory threadFactory = new CustomThreadFactory(new ExceptionHandler());
        ExecutorService threadPool2 = Executors.newFixedThreadPool(4, threadFactory);

        Runnable r = () -> {
            throw new RuntimeException("Exception from pool");
        };

        threadPool2.execute(r);
        threadPool2.shutdown();
        threadPool2.awaitTermination(10, TimeUnit.SECONDS);
    }
}
