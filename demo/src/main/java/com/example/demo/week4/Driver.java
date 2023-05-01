package com.example.demo.week4;

import java.util.concurrent.ForkJoinPool;

public class Driver {
    public static void main(String[] args) {
        try{

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int arr[] = new int[1000];
        for(int i=1;i<=1000;i++){
            arr[i-1]=i;
        }
        CountInstance countInstance = new CountInstance(arr, 0, arr.length-1);
        int result = forkJoinPool.invoke(countInstance);
        System.out.println("There are " + result + " ocurrences of 9");
    }catch(Exception e){
        System.err.println(e.getClass().getSimpleName());
    }
    }
}
