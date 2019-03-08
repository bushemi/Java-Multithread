package com.da.lect4.threadpools.exception.example3;

import java.util.concurrent.*;

public class ExceptionFromFutureObject {

    public static void main(String[] args) throws InterruptedException {
        //catching exception while obtaining result with future object
        ExecutorService threadPool3 = Executors.newFixedThreadPool(4);
        Callable<String> c3 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new Exception("Exception from task.");
            }
        };

        Future<String> executionResult = threadPool3.submit(c3);
        threadPool3.shutdown();
        threadPool3.awaitTermination(10, TimeUnit.SECONDS);

        try {
            executionResult.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            System.out.println(cause.getMessage());
        }
    }
}
