package com.da.lect2.unfair;

public class UnfairExample {
    public static void main(String[] agrs) {

        final Object LOCK = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (LOCK) {
                        System.out.println("A");
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (LOCK) {
                        System.out.println("B");
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (LOCK) {
                        System.out.println("C");
                    }
                }

            }
        }).start();
    }
}
