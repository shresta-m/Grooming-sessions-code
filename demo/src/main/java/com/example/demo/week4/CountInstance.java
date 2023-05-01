package com.example.demo.week4;

import java.util.concurrent.RecursiveTask;

public class CountInstance extends RecursiveTask<Integer> {
    private int[] arr;
    private int start;
    private int end;
    private final int threshold = 100;

    public CountInstance(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int arrLength = end - start + 1;
        if (arrLength == threshold)
            return countInstance();
        else {
            CountInstance subtask_1 = new CountInstance(arr, 0, 99);
            CountInstance subtask_2 = new CountInstance(arr, 100, 199);
            CountInstance subtask_3 = new CountInstance(arr, 200, 299);
            CountInstance subtask_4 = new CountInstance(arr, 300,399);
            CountInstance subtask_5 = new CountInstance(arr, 400,499);
            CountInstance subtask_6 = new CountInstance(arr, 500, 599);
            CountInstance subtask_7 = new CountInstance(arr, 600, 699);
            CountInstance subtask_8 = new CountInstance(arr, 700, 799);
            CountInstance subtask_9 = new CountInstance(arr, 800,899);
            CountInstance subtask_10 = new CountInstance(arr, 900,999);
             subtask_1.fork();
             subtask_2.fork();
             subtask_3.fork();
             subtask_4.fork();
             subtask_5.fork();
             subtask_6.fork();
             subtask_7.fork();
             subtask_8.fork();
             subtask_9.fork();
             subtask_10.fork();
             int count = subtask_1.join() + subtask_2.join() + subtask_3.join() + subtask_4.join() + subtask_5.join()
                     + subtask_6.join() + subtask_7.join() + subtask_8.join() + subtask_9.join()+ subtask_10.join();
             return count;
        }

    }

    private int countInstance() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (hasNine(this.arr[i]))
                count++;
        }
        System.out.println("The count of occurrence of 9 in the range "+ start + " and " + end + " is "+count);
        return count;

    }

    public Boolean hasNine(int number) {
        while (number > 0) {
            if (number % 10 == 9)
                return true;
            number = number / 10;
        }
        return false;
    }
}
