package com.da.lect2.synchr;

public class SomeClass {
    private final Object PRIVATE_LOCK_OBJECT = new Object();

    public synchronized void firstMethod() {
    }

    public void theSameAsFirstMethod() {
        synchronized(this) {

        }
    }

    public void theBestMethodUsingSynchr() {
        synchronized(PRIVATE_LOCK_OBJECT) {
        }
    }

    public static synchronized void synchronizedOnStaticMethod1() {
    }


    public static void synchronizedOnStaticMethod() {
        synchronized(SomeClass.class) {
        }
    }

}

