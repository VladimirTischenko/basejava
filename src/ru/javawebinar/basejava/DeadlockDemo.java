package ru.javawebinar.basejava;

public class DeadlockDemo {
    // Два объекта-ресурса
    public final static Object one = new Object(), two = new Object();

    public static void main(String[] s) {
        // Создаем два потока, которые будут
        // конкурировать за доступ к объектам
        // one и two

        Thread t1 = new Thread(() -> {
            // Блокировка первого объекта
            synchronized (one) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
                // Блокировка второго объекта
                synchronized (two) {
                    System.out.println("Success!");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            // Блокировка второго объекта
            synchronized (two) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
                // Блокировка первого объекта
                synchronized (one) {
                    System.out.println("Success!");
                }
            }
        });

        // Запускаем потоки
        t1.start();
        t2.start();
    }
}
