package homework.task1_1;

import java.time.LocalDateTime;

import static homework.task1_1.FirstTask.MONITOR;

class MyThread extends Thread {
private boolean isWaiting = true;
    MyThread() {
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public void run() {

        try {
            LocalDateTime now = LocalDateTime.now();
            synchronized (MONITOR) {
                MONITOR.notify();
                MONITOR.wait();
                while (LocalDateTime.now().isAfter(now.plusSeconds(3))) {
                    blockMonitor();
                }
            }
            MyThread myThread = new MyThread();
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (Exception exc) {
            System.out.println( "MyThread was interrupted.");
        }
    }

    void waitMonitor(Thread thread) throws InterruptedException {
        synchronized (MONITOR) {
            Thread.sleep(500);
            System.out.println("thread.getState() = " + thread.getState());
            MONITOR.notify();
        }
    }

    void blockMonitor() throws InterruptedException {
        while (isWaiting) {
            MONITOR.wait();
            synchronized (MONITOR) {
                return;
            }
        }
    }

}
