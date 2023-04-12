import java.util.Scanner;

public class SubstringCheck {
    public static void main(String args[]) {
        System.out.println("Enter 2 strings to check if 1st one is present in 2nd and vice-versa");
        String str1;
        String str2;
        try (Scanner in = new Scanner(System.in)) {
            str1 = in.next();
            str2 = in.next();
        }
        if (str1.contains(str2)) {
            System.out.println(str1 + " contains " + str2);
        }
        else if(str2.contains(str1)) {
            System.out.println(str2 + " contains " + str1);
        }
        else{
            System.out.println(str1 + " and " + str2 + " are completely different from each other");
        }
    }
}
