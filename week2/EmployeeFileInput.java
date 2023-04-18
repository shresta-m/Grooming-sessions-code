package week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

enum EmployeeType {
    CONTRACT,
    FULL_TIME
}

enum Status {
    ACTIVE,
    EXIT,
    SERVING_NOTICE
}

class EmployeeObj {
    int employeeId;
    String emp_name;
    String joiningDate;
    EmployeeType employeeType;
    String role;
    String domain;

    public EmployeeObj() {
    }

    public EmployeeObj(int employeeId, String emp_name, String joiningDate, EmployeeType employeeType, String role,
            String domain) {
        this.employeeId = employeeId;
        this.emp_name = emp_name;
        this.joiningDate = joiningDate;
        this.employeeType = employeeType;
        this.role = role;
        this.domain = domain;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = EmployeeType.CONTRACT.toString().equals(employeeType) ? EmployeeType.CONTRACT
                : EmployeeType.FULL_TIME;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "EmployeeObj{" +
                "employeeId=" + employeeId +
                ", emp_name='" + emp_name + '\'' +
                ", joiningDate=" + joiningDate +
                ", employeeType=" + employeeType +
                ", role='" + role + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}

public class EmployeeFileInput {

    private static Map<Integer, EmployeeObj> emp_data = new HashMap<>();

    public static Map<Integer, EmployeeObj> addEmployees(EmployeeObj employee) throws CustomException {
        if (emp_data.containsKey(employee.getEmployeeId())) {
            throw new CustomException("EmployeeObj with id " + employee.getEmployeeId() + " is already present in DB");
        }
        emp_data.put(employee.getEmployeeId(), employee);
        return emp_data;
    }

    public static void printList() {
        emp_data.forEach(
                (key, value) -> System.out.println(value.toString()));
    }

    public static void printEmpWithId(int id) throws CustomException {
        if (emp_data.containsKey(id)) {
            EmployeeObj emp = emp_data.get(id);
            System.out.println(emp.toString());
            return;
        }
        throw new CustomException("No Employees are present with id " + id);
    }

    public static List<EmployeeObj> getEmpWithrole(String role) throws CustomException {
        List<EmployeeObj> emp_role = new ArrayList<>();
        for (Map.Entry<Integer, EmployeeObj> data : emp_data.entrySet()) {
            if (data.getValue().getRole().equals(role)) {
                emp_role.add(data.getValue());
            }
        }
        if (emp_role.size() == 0) {
            throw new CustomException("No Employees are present with role " + role);
        }
        return emp_role;
    }

    public static List<EmployeeObj> getEmpWithAboveMinExp(int min_exp) throws CustomException {
        List<EmployeeObj> result = new ArrayList<>();
        LocalDate curDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Map.Entry<Integer, EmployeeObj> data : emp_data.entrySet()) {
            LocalDate date = LocalDate.parse(data.getValue().getJoiningDate(), formatter);
            if ((date != null) && (curDate != null)) {
                if (Period.between(date, curDate).getYears() >= min_exp) {
                    result.add(data.getValue());
                }
            }
        }
        if (result.size() == 0) {
            throw new CustomException("No Employees are present with min experience");
        }
        return result;

    }

    public static void main(String args[]) {
        try (BufferedReader br = new BufferedReader(new FileReader("week2\\employees.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                EmployeeObj emp = new EmployeeObj();
                emp.setEmployeeId(Integer.parseInt(values[0]));
                emp.setEmp_name(values[1]);
                emp.setJoiningDate(values[2]);
                emp.setEmployeeType(values[3]);
                emp.setRole(values[4]);
                emp.setDomain(values[5]);
                addEmployees(emp);
            }
            System.out.println("Employee data added successfully from csv file!!");
            int choice = 0;
            Boolean quit = false;
            try (Scanner in = new Scanner(System.in)) {
                while (!quit) {
                    System.out.println(
                            "\nChoose the options below to perform specified operations on Employee Data :\n1.Display the employee details given employee Id\n2.Display the list of employees given role\n3.Display the list of employees having experience >= given minimum years of experience\n4.Exit");
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter the Employee Id for details :");
                            printEmpWithId(in.nextInt());
                            break;
                        case 2:
                            System.out.println("Enter the role for getting list of employees :");
                            System.out.println(getEmpWithrole(in.next()).toString());
                            break;
                        case 3:
                            System.out.println("Enter the min experience for filtering the data :");
                            System.out.println(getEmpWithAboveMinExp(in.nextInt()).toString());
                            break;
                        case 4:
                            quit = true;
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (CustomException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
