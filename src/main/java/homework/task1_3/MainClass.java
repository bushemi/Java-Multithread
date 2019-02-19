package homework.task1_3;

public class MainClass {

    private static final String MONITOR = "monitor";
    private static final int PASSES_REQUIRED_QUANTITY = 3;
    private static int count = 0;

    //    PinPong на Java с помощью потоков. Есть два потока, когда "мяч" у первого потока, второй поток спит, когда мяч
//    у второго потока первый поток спит. Обеспечить "передачу" мяча несколько раз и корректно завершить программу.
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(MainClass::runForThread);
        first.setName("first");
        Thread second = new Thread(MainClass::runForThread);
        second.setName("second");

        first.start();
        Thread.sleep(500);
        second.start();

        first.join();
        second.join();

    }

    private static void runForThread() {
        try {
            boom();
            System.out.println("finished thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void boom() throws InterruptedException {
        for (int i = 0; i < PASSES_REQUIRED_QUANTITY; i++) {
            System.out.println("boom");
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            synchronized (MONITOR) {
                count++;
                MONITOR.notify();
                MONITOR.wait();
                if (count == PASSES_REQUIRED_QUANTITY * 2) MONITOR.notify();
            }
        }
    }
}
