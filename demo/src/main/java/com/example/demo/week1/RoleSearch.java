package com.example.demo.week1;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoleSearch {
    enum TechStacks {
        JAVA(8, "Manager", 0.45),
        SAP(4, "SR.Associate", 0.30),
        QA(3, "Associate", 0.20);

        private int yrs;
        private String role;
        private Double increment;

        public int getYears() {
            return this.yrs;
        }

        public String getRole() {
            return this.role;
        }

        public Double getIncValue() {
            return this.increment;
        }

        private TechStacks(int yrs, String role, Double increment) {
            this.yrs = yrs;
            this.role = role;
            this.increment = increment;
        }
    }

    public static String checkRoles(String ip) throws CustomException {
        TechStacks[] techStacks = TechStacks.values();
        for (TechStacks techStack : techStacks) {
            if (techStack.name().equals(ip)) {
                return ip;
            }
        }
        throw new CustomException("No open roles present for the given tech stack!!");
    }

    public static String checkExp(String ip, int exp) throws CustomException {
        TechStacks[] techStacks = TechStacks.values();
        for (TechStacks techStack : techStacks) {
            if (techStack.name().equals(ip)) {
                if (exp < techStack.getYears()) {
                    throw new CustomException(
                            "The years of your work experience does not meet the requirements. Better luck next time!!");
                }
            }
        }
        return ip;
    }

    public static Double calculateSalary(String stack, String role, Double curr_salary) {
        Double new_salary = 0.00;
        TechStacks[] techStacks = TechStacks.values();
        for (TechStacks techStack : techStacks) {
            if (techStack.name().equals(stack) && techStack.getRole().equals(role)) {
                new_salary = curr_salary + techStack.getIncValue() * curr_salary;
            }
        }
        return new_salary;
    }

    public static void main(String args[]) {
        String stack;
        int exp;
        String role;
        Double salary;
        System.out.println(
                "Enter the Tech stack, Years of experience in numbers ,Role and Current Salary in numbers as input");
        try (Scanner in = new Scanner(System.in)) {
            try {
                stack = in.next();
                exp = in.nextInt();
                role = in.next();
                salary = in.nextDouble();
                checkRoles(stack);
                checkExp(stack, exp);
                System.out.println("According to the input, the salary which we offer for the role is Rs."
                        + calculateSalary(stack, role, salary));
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
