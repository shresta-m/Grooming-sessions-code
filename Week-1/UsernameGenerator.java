import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.Period;
import java.time.format.*;

public class UsernameGenerator {
    public static boolean validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            return false;
        }
        name = name.replace(" ", "");
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateDate(String dob) {
        if (!dob.matches("[0-9]{2}[-]{1}[0-9]{2}[-]{1}[0-9]{4}")) {
            System.out.println(dob);
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dob, formatter);
        LocalDate curDate = LocalDate.now();
        // System.out.println(Period.between(date, curDate).getYears());
        if ((date != null) && (curDate != null)) {
            if (Period.between(date, curDate).getYears() < 18) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateAadharNum(String aadNumber) {
        if (aadNumber.length() != 12) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        String name = "";
        String dob;
        String aadno;
        String username;
        System.out.println(
                "Enter your name, Date of birth(dd-mm-yyyy), 12-digit Aadhar number for generating the username");
        try (Scanner in = new Scanner(System.in)) {
            try {
                name += in.nextLine();
                dob = in.nextLine();
                aadno = in.next();
                if (!validateName(name)) {
                    throw new CustomException("Enter Valid name with no digits and special characters");
                }
                if (!validateDate(dob)) {
                    throw new CustomException(
                            "Enter Valid DOB in format (dd-mm-yyyy) and the user age should be above 18yrs");
                }
                if (!validateAadharNum(aadno)) {
                    throw new CustomException("Enter the Valid 12 digit Aadhar number");
                }
                String[] x = name.split(" ");
                String a;
                if (x[0].length() >= 4) {
                    a = x[0].substring(0, 4);
                } else {
                    name = name.replace(" ", "");
                    a = name.substring(0, 4);
                }
                String[] y = dob.split("-");
                String b = y[1] + y[2];
                String c = aadno.substring(aadno.length() - 4);
                // 1st 4 letters of name + ddyyyy + last 4 digit of aad no
                username = a + b + c;
                System.out.println("The generated username for given input is " + username);

            } catch (Exception ex) {
                System.out.println("\n");
                if (ex.getClass().equals(InputMismatchException.class)) {
                    System.out.println("Enter Valid input with proper format as mentioned!!");
                }
                System.out.println("Exception name :" + ex.getClass() + "\nMessage :" + ex.getMessage());
            }
        }

    }

}
