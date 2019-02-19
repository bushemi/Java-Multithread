package homework.task1_1;

public class FirstTask {

    static final String MONITOR = "monitor";

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();


//        NEW,
        System.out.println("thread.getState() = " + thread.getState());
        thread.start();

//        RUNNABLE,
        System.out.println("thread.getState() = " + thread.getState());

//        BLOCKED
        thread.waitMonitor(thread);
        thread.setWaiting(false);

//        WAITING,
        thread.waitMonitor(thread);
        Thread.sleep(50);

//        TIMED_WAITING,
        System.out.println("thread.getState() = " + thread.getState());
        thread.join();

//        TERMINATED;
        System.out.println("thread.getState() = " + thread.getState());
        System.out.println("end of main");
    }


}
