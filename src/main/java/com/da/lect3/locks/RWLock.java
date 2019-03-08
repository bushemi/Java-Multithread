package com.da.lect3.locks;

import java.util.concurrent.locks.*;

public class RWLock {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Condition condition1 = reentrantLock.newCondition();
        condition.await();
        condition1.signal();
        condition.signalAll();



        reentrantLock.lock();
        //reentrantLock.lockInterruptibly();
        try {

        } finally {
            reentrantLock.unlock();
        }


        ReadWriteLock lock = new ReentrantReadWriteLock();

        Lock rLock1 = lock.readLock();
        Lock rLock2 = lock.readLock();
        Lock wLock1 = lock.writeLock();
        Lock wLock2 = lock.writeLock();
        //rLock1.newCondition();
        //wLock2.newCondition();

        System.out.println(rLock1 == rLock2);
        System.out.println(wLock1 == wLock2);
    }
}
