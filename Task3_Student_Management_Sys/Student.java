package CODSOFT.Task3_Student_Management_Sys;
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L; // Version control for serialization
    
    private String name;
    private int rollNumber;
    private String grade;
    private String email;

    public Student(String name, int rollNumber, String grade, String email) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
    }

    //Getters and Setters (Encapsulation) ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRollNumber() { return rollNumber; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Roll: " + rollNumber + " | Name: " + name + " | Grade: " + grade;
    }
}
