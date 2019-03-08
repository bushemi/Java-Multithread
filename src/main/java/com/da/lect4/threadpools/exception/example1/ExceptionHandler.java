package com.da.lect4.threadpools.exception.example1;

import java.util.Arrays;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable t) {
        System.out.println("Uncaught exception is detected! " + t + " st: " + Arrays.toString(t.getStackTrace()));
    }

}
