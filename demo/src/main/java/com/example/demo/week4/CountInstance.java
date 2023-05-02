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
        if (arrLength <= threshold) {
            System.out.println(Thread.currentThread().getName());
            return countInstance();
        }
        else {
            CountInstance subtask_1 = new CountInstance(arr, start, start+threshold-1);
            CountInstance subtask_2 = new CountInstance(arr, start+threshold, end);
            subtask_1.fork();
            subtask_2.fork();

            return subtask_1.join() + subtask_2.join();
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
