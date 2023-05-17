package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class DemoApplication {
    public String con;
    public String driverName;
    public String username;
    public String password;

    public DemoApplication(String driverName,String con,String username, String password){
        this.con=con;
        this.driverName=driverName;
        this.username=username;
        this.password=password;
    }

    public static void getPercent(Connection conn) throws SQLException {
        System.out.println("Overall % each student");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT s.id, s.name, ((SUM(s.marks) / (Count(s.courseId)*100) )* 100) AS percentage\n" +
                "FROM Students s\n" +
                "GROUP BY s.id, s.name;");
        System.out.printf("| %2s | %5s | %3s |\n", "ID", "NAME", "Percentage");
        while (rs.next()) {
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getDouble(3));
        }
        ps.close();
    }
    public static void getTopAndBottom(Connection conn) throws SQLException {
        System.out.println("Top 3 and bottom 3 students in each course along with course details");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT c.id as course_id, c.name as course_name, s.name as student_name, s.marks\n" +
                "FROM (\n" +
                "  SELECT courseId, marks, name, \n" +
                "    ROW_NUMBER() OVER (PARTITION BY courseId ORDER BY marks DESC) as rank_desc, \n" +
                "    ROW_NUMBER() OVER (PARTITION BY courseId ORDER BY marks ASC) as rank_asc \n" +
                "  FROM Students\n" +
                ") s\n" +
                "JOIN Course c ON s.courseId = c.id\n" +
                "WHERE s.rank_desc <= 3 OR s.rank_asc <= 3\n" +
                "ORDER BY c.id, s.rank_desc;  ");
        System.out.println("Columns : Id, CourseName,StudentName, Marks");
        while (rs.next()) {
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
        }
        ps.close();
    }
    public static void getFailedData(Connection conn) throws SQLException {
        System.out.println("List of students who failed in atleast one subject along with course and prof details.");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT s.name AS student_name, c.name AS course_name, p.name AS professor_name, s.marks,c.passmarks\n" +
                "FROM Students s\n" +
                "JOIN Course c ON s.courseId = c.id\n" +
                "JOIN Professor p ON c.professorId = p.id\n" +
                "WHERE s.marks < c.passMarks\n" +
                "GROUP BY s.id, c.id\n" +
                "HAVING COUNT(*) >= 1\n" +
                "ORDER BY c.name;");
        System.out.println("Columns : StudentName, CourseName, ProfessorName, StudentMarks, CoursePassMarks");
        while (rs.next()) {
            System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));
        }
        ps.close();
    }
    public static void getPassPercent(Connection conn) throws SQLException {
        System.out.println("Pass percentage of each course along with course and professor details");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT c.id as course_id, c.name as course_name, p.id as professor_id, p.name as professor_name, \n" +
                "  COUNT(CASE WHEN s.marks >= c.passMarks THEN 1 END) * 100 / COUNT(*) as pass_percentage\n" +
                "FROM Students s\n" +
                "JOIN Course c ON s.courseId = c.id\n" +
                "JOIN Professor p ON c.professorId = p.id\n" +
                "GROUP BY c.id, p.id;");
        System.out.println("Columns : CourseId, CourseName, ProfessorId, ProfessorName, PassPercentage");
        while (rs.next()) {
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getDouble(5));
        }
        ps.close();
    }
    public static void getAvgMarks(Connection conn) throws SQLException {
        System.out.println("Professor details along with the average marks obtained in the course he/she taught.");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT p.id AS professor_id, p.name AS professor_name, \n" +
                "c.id AS course_id, c.name AS course_name, AVG(s.marks) AS avg_marks\n" +
                "FROM Professor p\n" +
                "INNER JOIN Course c ON p.id = c.professorId\n" +
                "INNER JOIN Students s ON c.id = s.courseId\n" +
                "GROUP BY p.id, c.id");
        System.out.println("Columns : ProfessorId, ProfessName, CourseId, CourseName, AverageMarks");
        while (rs.next()) {
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getDouble(5));
        }
        ps.close();
    }
    public static void getFiftyPassPercent(Connection conn) throws SQLException {
        System.out.println("Professor details  along with course details who have atleast 50% pass percentage in their subject");
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery ("SELECT p.id AS professor_id, p.name AS professor_name, c.id AS course_id, c.name AS course_name, \n" +
                "       AVG(s.marks) AS average_marks, c.passMarks, \n" +
                "       (COUNT(CASE WHEN s.marks >= c.passMarks THEN 1 END) / COUNT(*)) * 100 AS pass_percentage\n" +
                "FROM Professor p\n" +
                "JOIN Course c ON p.id = c.professorId\n" +
                "JOIN Students s ON c.id = s.courseId\n" +
                "GROUP BY p.id, c.id\n" +
                "HAVING pass_percentage >= 50;");
        System.out.println("Columns : ProfessorId, ProfessName, CourseId, CourseName, AverageMarks,PassMarks, PassPercentage");
        while (rs.next()) {
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getDouble(5)+" "+rs.getDouble(6)+" "+rs.getDouble(7));
        }
        ps.close();
    }

    public static void main(String[] args) throws Exception {
        try{
            DemoApplication connection = new DemoApplication("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/collegeDB","root","root@123&456");
            Class.forName(connection.driverName);
            Connection conn = DriverManager.getConnection(connection.con,connection.username,connection.password);
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            int continue_choice =1;
            while(continue_choice!=0){
                System.out.println("Below are the available options :");
                System.out.println("1.Overall % each student\n2.Top 3 and bottom 3 students in each course along with course details\n" +
                        "3.List of students who failed in at least one subject along with course and prof details\n" +
                        "4.Pass percentage of each course along with course and professor details\n" +
                        "5.Professor details along with the average marks obtained in the course he/she taught\n"+
                        "6.Professor details  along with course details who have at least 50% pass percentage in their subject");
                System.out.println("Enter your choice of operation : ");
                int operation_choice = Integer.parseInt(buff.readLine());
                switch(operation_choice){
                    case 1:
                        getPercent(conn);
                        break;
                    case 2:
                        getTopAndBottom(conn);
                        break;
                    case 3:
                        getFailedData(conn);
                        break;
                    case 4:
                        getPassPercent(conn);
                        break;
                    case 5:
                        getAvgMarks(conn);
                        break;
                    case 6:
                        getFiftyPassPercent(conn);
                        break;
                    default:System.out.println("No match Case found !!");
                }
                System.out.println("Do you want to continue? Enter 1 or 0 , where 1 means Yes");
                continue_choice=Integer.parseInt(buff.readLine());
            }
            conn.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

