package com.da.lect2.interrupt;

public class SomeClass implements Runnable {

    @Override
    public void run() {
        //example how heavy computations can be interrupted
        while (!Thread.currentThread().isInterrupted()) {
            someHeavyComputations();
        }
    }

    private void someHeavyComputations() {
    }
}
