package com.example.demo.week2;

import java.util.LinkedList;

public class PalindromeCheck {

    public static boolean checkPalindrome(LinkedList<String> list) {
        int length = list.size();
        for (int i = 0; i < length / 2; i++) {
            System.out.println(i + " "+ (length -i-1));
            if (list.get(i) != list.get(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        if(checkPalindrome(list)){
            System.out.println("The given String is a palindrome");
        }else{
            System.out.println("The given String is not a palindrome");
        }
    }

}
