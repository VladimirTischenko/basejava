package ru.javawebinar.basejava;

public class DeadlockDemo {
    // Два объекта-ресурса
    public static final String S1 = "lock1", S2 = "lock2";

    public static void main(String[] s) {
        deadLock(S1, S2);
        deadLock(S2, S1);
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " Waiting " + lock1);
            // Блокировка первого объекта
            synchronized (lock1) {
                System.out.println(threadName + " Holding " + lock1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
                System.out.println(threadName + " Waiting " + lock2);
                // Блокировка второго объекта
                synchronized (lock2) {
                    System.out.println(threadName + " Holding " + lock2);
                }
            }
        }).start();
    }
}
