package com.da.lect4.threadpools.exception.example1;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    private final Thread.UncaughtExceptionHandler handler;

    public CustomThreadFactory(Thread.UncaughtExceptionHandler handler) {
        this.handler = handler;
    }

    @Override
    public Thread newThread(Runnable run) {
        Thread thread = Executors.defaultThreadFactory().newThread(run);
        thread.setUncaughtExceptionHandler(handler);
        return thread;
    }
}
