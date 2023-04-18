package week2;

import java.time.LocalDate;
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

class Employee {
    int employeeId;
    String emp_name;
    String joiningDate;
    EmployeeType employeeType;
    String role;
    Status status;

    Employee() {
    }

    Employee(int employeeId, String emp_name, String date, EmployeeType employeeType, String role, Status status) {
        this.employeeId = employeeId;
        this.emp_name = emp_name;
        this.joiningDate = date;
        this.employeeType = employeeType;
        this.role = role;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.ACTIVE.toString().equals(status) ? Status.ACTIVE
                : (Status.EXIT.toString().equals(status) ? Status.EXIT : Status.SERVING_NOTICE);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", emp_name='" + emp_name + '\'' +
                ", joiningDate=" + joiningDate +
                ", employeeType=" + employeeType +
                ", role='" + role + '\'' +
                ", status=" + status +
                '}';
    }

}

public class EmployeeCollection {
    private static Map<Integer, Employee> emp_data = new HashMap<>();

    public static Map<Integer, Employee> addEmployees(Employee employee) throws CustomException {
        if (emp_data.containsKey(employee.getEmployeeId())) {
            throw new CustomException("Employee with id " + employee.getEmployeeId() + " is already present in DB");
        }
        emp_data.put(employee.getEmployeeId(), employee);
        return emp_data;
    }

    public static Map<Integer, Employee> deletEmployees(int id) throws CustomException {
        if (emp_data.containsKey(id)) {
            emp_data.remove(id);
            return emp_data;
        }
        throw new CustomException("Employee with id " + id + " not present in DB");

    }

    public static Map<Integer, Employee> updateType(int id, String type) throws CustomException {
        if (emp_data.containsKey(id)) {
            Employee emp = emp_data.get(id);
            emp.setEmployeeType(type);
            emp_data.put(id, emp);
            return emp_data;
        }
        throw new CustomException("Employee with id " + id + " not present in DB");
    }

    public static Map<Integer, Employee> updateRole(int id, String role) throws CustomException {
        if (emp_data.containsKey(id)) {
            Employee emp = emp_data.get(id);
            emp.setRole(role);
            emp_data.put(id, emp);
            return emp_data;
        }
        throw new CustomException("Employee with id " + id + " not present in DB");

    }

    public static Employee getSnrEmp() {
        Employee oldest = emp_data.entrySet().iterator().next().getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(oldest.getJoiningDate(), formatter);
        LocalDate curDate = LocalDate.now();
        for (Map.Entry<Integer, Employee> data : emp_data.entrySet()) {
            LocalDate date1 = LocalDate.parse(data.getValue().getJoiningDate(), formatter);
            LocalDate date2 = LocalDate.parse(oldest.getJoiningDate(), formatter);
            System.out.println(curDate.compareTo(date) +" "+curDate.compareTo(date1));
            if (curDate.compareTo(date1) > curDate.compareTo(date2)) {
                oldest = data.getValue();
            }
        }
        return oldest;
    }

    public static List<Employee> getEmpWithStatus(String status) throws CustomException {
        List<Employee> emp_status = new ArrayList<>();
        for (Map.Entry<Integer, Employee> data : emp_data.entrySet()) {
            if (data.getValue().getStatus().toString().equals(status)) {
                emp_status.add(data.getValue());
            }
        }
        if (emp_status.size() == 0) {
            throw new CustomException("No Employees are present with status " + status);
        }
        return emp_status;
    }

    public static void printList() {
        emp_data.forEach(
                (key, value) -> System.out.println(value.toString()));
    }

    public static void printEmpWithId(int id) throws CustomException{
        if(emp_data.containsKey(id)){
            Employee emp = emp_data.get(id);
            System.out.println(emp.toString());
            return;
        }
        throw new CustomException("No Employees are present with id " + id);   
    }

    public static void main(String args[]) {
        Employee emp1 = new Employee(1, "Shresta", "23-02-2020", EmployeeType.FULL_TIME, "Developer",
                Status.ACTIVE);
        Employee emp2 = new Employee(2, "Shrushti", "23-02-2010", EmployeeType.CONTRACT, "Manager",
                Status.SERVING_NOTICE);
        Employee emp3 = new Employee(3, "Shreya", "23-02-2015", EmployeeType.FULL_TIME, "Associate",
                Status.ACTIVE);

        int choice = 0;
        int id;
        Boolean quit = false;
        try (Scanner in = new Scanner(System.in)) {
            try {
                addEmployees(emp1);
                addEmployees(emp2);
                addEmployees(emp3);
                while (!quit) {
                    System.out.println(
                    "\nChoose the options below to perform specified operations on Employee Data :\n1.Add a new Employee\n2.Delete an existing employee\n3.Update the Employment Type of an employee\n4.Update the Role of an employee\n5.Get the senior most employees in the company\n6.Get the list of employees given a particular status\n7.Print all employees\n8.Exit");
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println(
                                    "Provide the Employee details for adding new employee. Enter employeeId, Name, Joining date( dd-mm-yyyy),Employment Type, role and Status");
                            Employee emp = new Employee();
                            emp.setEmployeeId(in.nextInt());
                            emp.setEmp_name(in.next());
                            emp.setJoiningDate(in.next());
                            emp.setEmployeeType(in.next());
                            emp.setRole(in.next());
                            emp.setStatus(in.next());
                            addEmployees(emp);
                            printList();
                            System.out.println("Employee Added successfully!!!");
                            break;
                        case 2:
                            System.out.println("Enter the Employee Id for deletion:");
                            deletEmployees(in.nextInt());
                            System.out.println("Employee Deletion successful!!!");
                            break;
                        case 3:
                            System.out.println("Enter the employee Id and Employment type for Data updation:");
                            printList();
                            id = in.nextInt();
                            updateType(id, in.next());
                            System.out.println("Employee Updation successful!!!");
                            printEmpWithId(id);
                            break;
                        case 4:
                            System.out.println("Enter the employee Id and role for Data updation :");
                            printList();
                            id = in.nextInt();
                            updateRole(id, in.next());
                            System.out.println("Employee Updation successful!!!");
                            printEmpWithId(id);
                            break;
                        case 5:
                            System.out.println("Here is the data related to senior most employee of the company : "
                                    + getSnrEmp().toString());
                            break;
                        case 6:
                            System.out.println("Enter the status to get all employee wrt to the given status :");
                            List<Employee> result = getEmpWithStatus(in.next());
                            System.out.println("Here is your requested data :\n");
                            System.out.println(result.toString());
                            break;
                        case 7:
                            printList();
                            break;
                        case 8:
                            quit = true;
                            break;
                        default:
                            printList();
                            break;

                    }
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
