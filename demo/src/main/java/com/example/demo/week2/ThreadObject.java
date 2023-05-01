package com.example.demo.week2;

class ThreadGenerator implements Runnable {

    private int rem;
    private static int i = 0;
    static Object lock = new Object();

    public ThreadGenerator(int rem) {
        this.rem = rem;
    }

    @Override
    public void run() {
        while (i < 21) {
            synchronized (lock) {
                while (i % 2 != rem) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + i);
                i++;
                lock.notifyAll();
            }
        }
    }

}

public class ThreadObject {
    public static void main(String args[]) {

        ThreadGenerator evenThread = new ThreadGenerator(0);
        ThreadGenerator oddThread = new ThreadGenerator(1);
        Thread thread1 = new Thread(evenThread);
        Thread thread2 = new Thread(oddThread);
        thread1.start();
        thread2.start();
    }
}