/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentmanagementsystem;

/**
 *
 * @author AYUSH SHARMA
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String course;
    private int mark1;
    private int mark2;
    private int mark3;

    public Student(int id, String name, int age, String course, int mark1, int mark2, int mark3) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public int getId() {
        return id;
    }

    public int getTotal() {
        return mark1 + mark2 + mark3;
    }

    public double getPercentage() {
        return getTotal() / 3.0;
    }

    public String getGrade() {
        double percentage = getPercentage();

        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "Fail";
    }

    public void updateDetails(String name, int age, String course, int mark1, int mark2, int mark3) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public void displayStudent() {
        System.out.println("-----------------------------------");
        System.out.println("Student ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Age          : " + age);
        System.out.println("Course       : " + course);
        System.out.println("Mark 1       : " + mark1);
        System.out.println("Mark 2       : " + mark2);
        System.out.println("Mark 3       : " + mark3);
        System.out.println("Total Marks  : " + getTotal());
        System.out.printf("Percentage   : %.2f%%\n", getPercentage());
        System.out.println("Grade        : " + getGrade());
        System.out.println("-----------------------------------");
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadData();

        if (!login()) {
            System.out.println("Too many wrong attempts. Program closed.");
            return;
        }

        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    saveData();
                    System.out.println("Data saved. Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 to 6.");
            }

        } while (choice != 6);

        sc.close();
    }

    public static boolean login() {
        String correctUsername = "admin";
        String correctPassword = "1234";

        System.out.println("===== Admin Login =====");

        for (int i = 1; i <= 3; i++) {
            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Invalid login. Attempts left: " + (3 - i));
            }
        }

        return false;
    }

    public static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = readInt();

        if (findStudentById(id) != null) {
            System.out.println("Student with this ID already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = readNonEmptyString();

        System.out.print("Enter Student Age: ");
        int age = readAge();

        System.out.print("Enter Student Course: ");
        String course = readNonEmptyString();

        System.out.print("Enter Mark 1: ");
        int mark1 = readMark();

        System.out.print("Enter Mark 2: ");
        int mark2 = readMark();

        System.out.print("Enter Mark 3: ");
        int mark3 = readMark();

        students.add(new Student(id, name, age, course, mark1, mark2, mark3));
        saveData();

        System.out.println("Student added successfully.");
    }

    public static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n===== All Student Records =====");
        for (Student student : students) {
            student.displayStudent();
        }
    }

    public static void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        int id = readInt();

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("\nStudent found:");
            student.displayStudent();
        }
    }

    public static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = readInt();

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = readNonEmptyString();

        System.out.print("Enter New Age: ");
        int age = readAge();

        System.out.print("Enter New Course: ");
        String course = readNonEmptyString();

        System.out.print("Enter New Mark 1: ");
        int mark1 = readMark();

        System.out.print("Enter New Mark 2: ");
        int mark2 = readMark();

        System.out.print("Enter New Mark 3: ");
        int mark3 = readMark();

        student.updateDetails(name, age, course, mark1, mark2, mark3);
        saveData();

        System.out.println("Student updated successfully.");
    }

    public static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = readInt();

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            students.remove(student);
            saveData();
            System.out.println("Student deleted successfully.");
        }
    }

    public static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            sc.next();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    public static String readNonEmptyString() {
        String input;

        do {
            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print("Input cannot be empty. Enter again: ");
            }
        } while (input.isEmpty());

        return input;
    }

    public static int readAge() {
        int age;

        do {
            age = readInt();

            if (age <= 0 || age > 100) {
                System.out.print("Invalid age. Enter age between 1 and 100: ");
            }
        } while (age <= 0 || age > 100);

        return age;
    }

    public static int readMark() {
        int mark;

        do {
            mark = readInt();

            if (mark < 0 || mark > 100) {
                System.out.print("Invalid mark. Enter mark between 0 and 100: ");
            }
        } while (mark < 0 || mark > 100);

        return mark;
    }

    public static void saveData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(students);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public static void loadData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            students = (ArrayList<Student>) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            students = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data.");
            students = new ArrayList<>();
        }
    }
}
