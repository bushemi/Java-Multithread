package com.da.lect2.basic;

public class CreatingByInheritance extends Thread {
    @Override
    public void run() {
        Thread.currentThread().setName("This thread was created from main thread");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getPriority());
    }
}
