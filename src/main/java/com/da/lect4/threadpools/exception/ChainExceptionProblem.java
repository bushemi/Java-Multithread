package com.da.lect4.threadpools.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ChainExceptionProblem {

    private static List<Runnable> getTasks() {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(() -> {
            throw new RuntimeException("Houston we have a problem!");
        });
        tasks.add(() -> {
            throw new RuntimeException("SOS");
        });

        return tasks;
    }


    public static void main(String[] args) throws InterruptedException {
        final int nThreads = 8;
        // UnhandledExceptionHandler which will collect Exceptions:
        final ExceptionCollector exHandler = new ExceptionCollector();
        // create a executor with a custom Thread factory:
        final ExecutorService executor = Executors.newFixedThreadPool(8,
                new ThreadWithUncaughtExHandlerFactory(exHandler));

        for (final Runnable command : getTasks()) {
            executor.execute(command);
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        if (exHandler.getExceptions().size() > 0) {
            exHandler.getExceptions().forEach(ex -> System.out.println(ex.getMessage()));
        }
    }
}

class ExceptionCollector implements Thread.UncaughtExceptionHandler {
    private final List<Throwable> exceptions = Collections.synchronizedList(new LinkedList<Throwable>());

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        exceptions.add(e);
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }
}

class ThreadWithUncaughtExHandlerFactory implements ThreadFactory {
    private final Thread.UncaughtExceptionHandler exHandler;

    public ThreadWithUncaughtExHandlerFactory(final Thread.UncaughtExceptionHandler exHandler) {
        this.exHandler = exHandler;
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(exHandler);
        return t;
    }
}
