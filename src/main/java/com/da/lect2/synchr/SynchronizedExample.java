package com.da.lect2.synchr;

public class SynchronizedExample {

    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();
        Object lockC = new Object();

        //lockC lock does not know about lockA and lockB
        synchronized (lockA) {
            synchronized (lockB) {
                synchronized (lockC) {
                }
            }
        }
    }
}
