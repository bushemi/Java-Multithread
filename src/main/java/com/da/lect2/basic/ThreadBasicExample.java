package com.da.lect2.basic;

public class ThreadBasicExample {
    public static void main(String[] args) throws InterruptedException {
        //do not use strings as a monitor objects
        String str1 = "Hello!";
        String str2 = "Hello!";

        //example that main thread will wait forever
        final Object LOCK = new Object();
        synchronized (LOCK) {
            LOCK.wait();
        }

        //creating thread by inheriting of Thread class
        CreatingByInheritance thread = new CreatingByInheritance();
        thread.start();

        if (args.length > 0) {
            //creating thread by implementing Runnable interface
            new Thread(
                    () -> System.out.println("While creating thread was using lambda expression"))
                    .start();
        }

        //setting up common thread properties
        System.out.println(Thread.currentThread().getName());
        Thread.currentThread().setPriority(8);
        Thread.State state = Thread.currentThread().getState();
        System.out.println("State of main thread is " + state);

        //very bad exaple how to create thread
        Thread t = new Thread() {
            @Override
            public void run() {
            }
        };

        //highlight arbitrary variable press Atl+F8 and print Thread.currentThread().getStackTrace();
        //to get stack trace
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    }
}





