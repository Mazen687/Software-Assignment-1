import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;

public class App {
    static Scanner input = new Scanner(System.in);
    static int total_hours = 0;
    static int hour = 0;
    static double numerator = 0;
    static double last = 0;
    public static final Map<String, Double> GRADES = new HashMap<>();
    static List<Course> courses = new ArrayList<>();

    static {
        GRADES.put("A+", 4.0);
        GRADES.put("A", 3.7);
        GRADES.put("A-", 3.4);
        GRADES.put("B+", 3.2);
        GRADES.put("B", 3.0);
        GRADES.put("B-", 2.8);
        GRADES.put("C+", 2.6);
        GRADES.put("C", 2.4);
        GRADES.put("C-", 2.2);
        GRADES.put("D+", 2.0);
        GRADES.put("D", 1.5);
        GRADES.put("D-", 1.0);
        GRADES.put("F", 0.0);
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();

        }

    }

    static class Course {
        String name;
        int hours;
        String grade;

        Course(String name, int hours, String grade) {
            this.name = name;
            this.hours = hours;
            this.grade = grade.toUpperCase();
        }
    }

    public static void showMenu() {
        int choice = -1;

        while (true) {
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. Calculate");
            System.out.println("4. Show courses");
            System.out.println("5. Statistics");
            System.out.println("6. Exit");

            while (true) {
                System.out.print("Enter your choice: ");

                if (input.hasNextInt()) { // check if input is an integer
                    choice = input.nextInt(); // read the integer

                    if (choice >= 1 && choice <= 6) {
                        break;
                    } else {
                        System.out.println("Invalid choice! Must be between 1 and 6.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter an integer.");
                    input.nextLine(); // discard invalid input
                }
            }

            // Menu actions
            switch (choice) {
                case 1:
                    Add();
                    break;
                case 2:
                    Remove();
                    break;
                case 3:
                    Calc();
                    break;
                case 4:
                    Show();
                    break;
                case 5:
                    Stat();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }

    public static void Add() {
        input.nextLine();
        System.out.print("Enter Course Name: ");
        String name = input.nextLine();

        while (true) {
            System.out.print("Enter Credit Hours: ");
            if (input.hasNextInt()) {
                hour = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input! Please enter an integer.");
                input.nextLine();
            }
        }
        input.nextLine();
        String grade;
        while (true) {
            System.out.print("Enter your grade (A+, A, A-, ... F): ");
            grade = input.next().toUpperCase();
            input.nextLine();
            if (GRADES.containsKey(grade)) {
                break;
            } else {
                System.out.println("Invalid grade! Please try again.");
            }
        }
        // create object and put it in list
        Course c = new Course(name, hour, grade);
        courses.add(c);
        // This is your key
        Double value = GRADES.get(grade);
        total_hours += hour;
        last = hour * value;// for remove function
        numerator += hour * value;

    }

    public static void Remove() {
        if (courses.isEmpty()) {
            System.out.println("No courses to remove!");
            return;
        }
        Show();

        System.out.print("Enter the name of the course to remove: ");
        input.nextLine();
        String nameToRemove = input.nextLine();

        // Find the course
        boolean removedFlag = false;
        Iterator<Course> it = courses.iterator();
        while (it.hasNext()) {
            Course c = it.next();
            if (c.name.equalsIgnoreCase(nameToRemove)) {
                total_hours -= c.hours;
                numerator -= c.hours * GRADES.get(c.grade);
                it.remove();
                System.out.println("Removed course: " + c.name + " | Grade: " + c.grade + " | Hours: " + c.hours);
                removedFlag = true;
                break;
            }
        }

        if (!removedFlag) {
            System.out.println("Course not found!");
        }

    }

    public static void Calc() {
        double final_grade = numerator / total_hours;

        System.out.println("Your GPA: " + final_grade);

    }

    public static void Show() {
        if (courses.isEmpty()) {
            System.out.println("No courses added yet!");
            return;
        }

        System.out.printf("%-15s %-7s %-7s%n", "Course", "Hours", "Grade");
        System.out.println("----------------------------------");

        // Print each course
        for (Course c : courses) {
            System.out.printf("%-15s %-7s %-7s%n", c.name, c.hours, c.grade);
        }

        System.out.println("----------------------------------");
        System.out.println("Total Hours: " + total_hours);

    }

    public static void Stat() {
        HighestGrade();
        LowestGrade();
        CountCoursesPerGrade();

    }

    public static void HighestGrade() {
        if (courses.isEmpty()) {
            System.out.println("No courses added yet!");
            return;
        }

        // Step 1: find the maximum GPA value
        double maxGPA = GRADES.get(courses.get(0).grade); // here
        for (Course c : courses) {
            double gpa = GRADES.get(c.grade); // here
            if (gpa > maxGPA) {
                maxGPA = gpa;
            }
        }

        // Step 2: find the grade string corresponding to maxGPA
        String maxGrade = "";
        for (Map.Entry<String, Double> entry : GRADES.entrySet()) {
            if (entry.getValue() == maxGPA) {
                maxGrade = entry.getKey();
                break;
            }
        }

        // Step 3: print header once with the grade
        System.out.println("Highest Grade: " + maxGrade);
        System.out.printf("%-15s %-7s%n", "Course", "Hours");
        System.out.println("-------------------------");

        // Step 4: print all courses with this grade
        for (Course c : courses) {
            if (c.grade.equals(maxGrade)) {
                System.out.printf("%-15s %-7s%n", c.name, c.hours);
            }
        }

        System.out.println("-------------------------");
    }

    public static void LowestGrade() {
        if (courses.isEmpty()) {
            // System.out.println("No courses added yet!");
            return;
        }

        // Step 1: find the minimum GPA value
        double minGPA = GRADES.get(courses.get(0).grade);
        for (Course c : courses) {
            double gpa = GRADES.get(c.grade);
            if (gpa < minGPA) {
                minGPA = gpa;
            }
        }

        // Step 2: find the grade string corresponding to minGPA
        String minGrade = "";
        for (Map.Entry<String, Double> entry : GRADES.entrySet())// entrySet for key and value
        {
            if (entry.getValue() == minGPA) {
                minGrade = entry.getKey();
                break;
            }
        }

        // Step 3: print header once with the grade
        System.out.println("Lowest Grade: " + minGrade);
        System.out.printf("%-15s %-7s%n", "Course", "Hours");
        System.out.println("-------------------------");

        // Step 4: print all courses with this grade
        for (Course c : courses) {
            if (c.grade.equals(minGrade)) {
                System.out.printf("%-15s %-7s%n", c.name, c.hours);
            }
        }

        System.out.println("-------------------------");
    }

    public static void CountCoursesPerGrade() {
        if (courses.isEmpty()) {
            // System.out.println("No courses added yet!");
            return;
        }

        Map<String, Integer> gradeCount = new HashMap<>();

        for (Course c : courses) {
            gradeCount.put(c.grade, gradeCount.getOrDefault(c.grade, 0) + 1);
        }

        System.out.println("Number of courses per grade:");
        for (String grade : gradeCount.keySet()) {
            System.out.println(grade + ": " + gradeCount.get(grade));
        }
    }

}

