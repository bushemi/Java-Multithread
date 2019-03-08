package com.da.lect3.locks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {

    //Создаем CountDownLatch на 8 "условий"
    private static final CountDownLatch START = new CountDownLatch(8);
    //Условная длина гоночной трассы
    private static final int TRACK_LENGTH = 500_000;

    public static void main(String[] args) throws InterruptedException {
        int carSpeed = 0;
        for (int i = 1; i <= 5; i++) {
            carSpeed = (int) (Math.random() * 100 + 50);
            new Thread(new Car(i, carSpeed)).start();
            Thread.sleep(1000);
        }

        //Проверяем, собрались ли все автомобили
        //у стартовой прямой. Если нет, ждем 100ms
        while (START.getCount() > 3) {
            Thread.sleep(100);
        }


        Thread.sleep(1000);
        System.out.println("На старт!");
        START.countDown();//Команда дана, уменьшаем счетчик на 1
        START.countDown();//Команда дана, уменьшаем счетчик на 1
        Thread.sleep(1000);
        System.out.println("Внимание!");
        Thread.sleep(1000);
        System.out.println("Марш!");
        START.countDown();
        //Команда дана, уменьшаем счетчик на 1
        //счетчик становится равным нулю, и все ожидающие потоки
        //одновременно разблокируются
    }

    public static class Car implements Runnable {
        private int carNumber;
        //считаем, что скорость автомобиля постоянная
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Автомобиль №%d подъехал к стартовой прямой.\n", carNumber);
                //Автомобиль подъехал к стартовой прямой - условие выполнено
                //уменьшаем счетчик на 1
                START.countDown();
                //метод await() блокирует поток, вызвавший его, до тех пор, пока
                //счетчик CountDownLatch не станет равен 0
                START.await();
                Thread.sleep(TRACK_LENGTH / carSpeed);//ждем пока проедет трассу
                System.out.printf("Автомобиль №%d финишировал!\n", carNumber);
            } catch (InterruptedException e) {
                new Error("Never be interrupted");
            }
        }
    }
}
