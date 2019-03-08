package com.da.lect4.threadpools.exception.example2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AterExecuteMethodExceptionHandling {

    public static void main(String[] args) throws InterruptedException {
        //exception handling in execute method
        ExecutorService threadPool1 = new ThreadPoolHandlingException(4, 4, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>());

        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Executed!");
                throw new Exception("exception from call method");
            }
        };

        threadPool1.submit(c);
        threadPool1.shutdown();
        threadPool1.awaitTermination(10, TimeUnit.SECONDS);
    }
}
