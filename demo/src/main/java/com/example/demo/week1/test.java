package com.example.demo.week1;

import java.util.Scanner;

class test {
    public static void main(String args[]) {
        int x;
        System.out.println("Enter an integer to check if it is odd or even ");
        try (Scanner in = new Scanner(System.in)) {
            x = in.nextInt();
        }
        // if (x % 2 == 0)
        // System.out.println("You entered an even number.");
        // else
        // System.out.println("You entered an odd number.");
        // int fact=1;
        // for(int i=1;i<=x;i=i+1){
        // fact = fact * i;
        // }
        // System.out.println("Factorial :"+ fact);
        int reverse = 0;
        while (x != 0) {
            reverse = reverse * 10;
            reverse = reverse + x % 10;
            x = x / 10;
        }
        System.out.println("Reverse of entered number is " + reverse);
    }
}