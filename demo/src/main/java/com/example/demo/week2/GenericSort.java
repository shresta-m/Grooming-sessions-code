package com.example.demo.week2;

import java.util.Arrays;

public class GenericSort {
    static <T extends Comparable<T>> void sort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                 if (arr[j].compareTo(arr[j+1])> 0) {
                      T temp = arr[j];
                      arr[j] = arr[j+1];
                      arr[j+1] = temp;
                 }
             }
        }
    }

    public static void main(String args[]){
        String[] a1 = {"T","Z","A"};
        sort(a1);
        System.out.println(Arrays.toString(a1));
        Integer[] a2 = {12,100,4,55,19034,34256};
        sort(a2);
        System.out.println(Arrays.toString(a2));
        Character[] a3 = {'z','a','s','m','t'};
        sort(a3);
        System.out.println(Arrays.toString(a3));



    }
    
}
