package com.da.lect3.locks;

import java.util.concurrent.Semaphore;

class Queue {
    private int value;

    private final Semaphore SEM_CONSUMER = new Semaphore(0);

    private Semaphore SEM_PRODUCER = new Semaphore(1);

    void get() {
        try {
            SEM_CONSUMER.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        System.out.println("Got: " + value);
        SEM_PRODUCER.release();
    }

    void put(int n) {
        try {
            SEM_PRODUCER.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }

        this.value = n;
        System.out.println("Put: " + n);
        SEM_CONSUMER.release();
    }
}

class Producer implements Runnable {
    private Queue q;
    private Thread producer;

    Producer(Queue q) {
        this.q = q;
        producer = new Thread(this, "Producer");
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; ++i) {
            q.put(i);
        }
    }

    public final void startProduce() {
        producer.start();
    }
}

class Consumer implements Runnable {
    private Queue q;
    private Thread consumer;

    Consumer(Queue q) {
        this.q = q;
        consumer = new Thread(this, "Consumer");
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; ++i) {
            q.get();
        }
    }

    public final void startConsume() {
        consumer.start();
    }
}


public class ProduserConsumer {
    public static void main(String args[]) {
        Queue q = new Queue();
        new Consumer(q).startConsume();
        new Producer(q).startProduce();
    }
}
