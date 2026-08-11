package CODSOFT.Task1_Grade_Calculator;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeCalculator extends JFrame {

    private JTextField txtStudentName;
    private JPanel subjectsPanel;
    private List<SubjectRow> subjectRows;
    private JLabel lblTotalDisplay, lblAvgDisplay, lblGradeDisplay;
    private JButton btnCompute, btnClear, btnAddSubject, btnSave;
    private String lastGrade = "N/A";
    private double lastTotal = 0;
    private double lastAvg = 0;

    public StudentGradeCalculator() {
        setTitle("CodSoft Internship | Dynamic Grade Calculator");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 250));
        
        subjectRows = new ArrayList<>();

        // 1. Header Section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        
        JLabel headerTitle = new JLabel("Student Assessment Portal", SwingConstants.CENTER);
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        headerPanel.add(headerTitle, BorderLayout.NORTH);

        JPanel studentInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        studentInfoPanel.setOpaque(false);
        JLabel lblName = new JLabel("Student Name:");
        lblName.setForeground(Color.WHITE);
        txtStudentName = new JTextField(15);
        studentInfoPanel.add(lblName);
        studentInfoPanel.add(txtStudentName);
        
        headerPanel.add(studentInfoPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Input Section (Center)
        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new BoxLayout(subjectsPanel, BoxLayout.Y_AXIS));
        subjectsPanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(subjectsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Subjects and Marks"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Right side controls (Add Subject)
        JPanel rightPanel = new JPanel(new FlowLayout());
        btnAddSubject = new JButton("Add Subject");
        styleButton(btnAddSubject, new Color(52, 152, 219));
        btnAddSubject.addActionListener(e -> addSubjectRow());
        rightPanel.add(btnAddSubject);
        
        // 4. Results & Action Section (South)
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayout(5, 1, 5, 5));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        footerPanel.setOpaque(false);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        btnCompute = new JButton("Calculate");
        btnClear = new JButton("Clear");
        btnSave = new JButton("Save Results");

        styleButton(btnCompute, new Color(46, 204, 113));
        styleButton(btnClear, new Color(231, 76, 60));
        styleButton(btnSave, new Color(155, 89, 182));
        
        buttonPanel.add(btnCompute);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);

        lblTotalDisplay = createResultLabel("Total Marks: 0");
        lblAvgDisplay = createResultLabel("Average Percentage: 0.0%");
        lblGradeDisplay = createResultLabel("Grade: N/A");
        lblGradeDisplay.setFont(new Font("Arial", Font.BOLD, 16));

        footerPanel.add(rightPanel);
        footerPanel.add(buttonPanel);
        footerPanel.add(lblTotalDisplay);
        footerPanel.add(lblAvgDisplay);
        footerPanel.add(lblGradeDisplay);

        add(footerPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnCompute.addActionListener(e -> performCalculation());
        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveResults());

        // Add initial 5 subjects
        String[] initialSubjects = {"Physics", "Chemistry", "Maths", "English", "Computer Science"};
        for (String sub : initialSubjects) {
            addSubjectRow(sub);
        }
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
    }

    private JLabel createResultLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Verdana", Font.PLAIN, 14));
        return lbl;
    }
    
    private void addSubjectRow() {
        addSubjectRow("Subject " + (subjectRows.size() + 1));
    }

    private void addSubjectRow(String defaultName) {
        SubjectRow row = new SubjectRow(defaultName);
        subjectRows.add(row);
        subjectsPanel.add(row.panel);
        subjectsPanel.revalidate();
        subjectsPanel.repaint();
    }
    
    private void removeSubjectRow(SubjectRow row) {
        subjectRows.remove(row);
        subjectsPanel.remove(row.panel);
        subjectsPanel.revalidate();
        subjectsPanel.repaint();
    }

    private void performCalculation() {
        if (subjectRows.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one subject.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalMarks = 0;
        int validSubjects = 0;

        for (SubjectRow row : subjectRows) {
            try {
                String text = row.txtMark.getText().trim();
                if (text.isEmpty()) continue; // skip empty
                double mark = Double.parseDouble(text);
                if (mark < 0 || mark > 100) {
                    throw new NumberFormatException("Marks must be between 0 and 100.");
                }
                totalMarks += mark;
                validSubjects++;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric marks (0-100) for subject: " + row.txtSubjectName.getText(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        if (validSubjects == 0) {
            JOptionPane.showMessageDialog(this, "No valid marks entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lastTotal = totalMarks;
        lastAvg = totalMarks / validSubjects;
        lastGrade = computeGrade(lastAvg);
        
        lblTotalDisplay.setText("Total Marks: " + (int)lastTotal + " / " + (validSubjects * 100));
        lblAvgDisplay.setText(String.format("Average Percentage: %.2f%%", lastAvg));
        lblGradeDisplay.setText("Grade: " + lastGrade);
        
        if (lastGrade.equals("F")) lblGradeDisplay.setForeground(Color.RED);
        else if (lastGrade.equals("A")) lblGradeDisplay.setForeground(new Color(34, 139, 34));
        else lblGradeDisplay.setForeground(Color.BLACK);
    }
    
    private String computeGrade(double avg) {
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    private void clearForm() {
        txtStudentName.setText("");
        for (SubjectRow row : subjectRows) {
            row.txtMark.setText("");
        }
        lblTotalDisplay.setText("Total Marks: 0");
        lblAvgDisplay.setText("Average Percentage: 0.0%");
        lblGradeDisplay.setText("Grade: N/A");
        lblGradeDisplay.setForeground(Color.BLACK);
        lastTotal = 0; lastAvg = 0; lastGrade = "N/A";
    }
    
    private void saveResults() {
        String name = txtStudentName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the student's name before saving.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (lastGrade.equals("N/A")) {
            JOptionPane.showMessageDialog(this, "Please calculate the grades before saving.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (FileWriter fw = new FileWriter("student_results.csv", true)) {
            fw.write(name + "," + lastTotal + "," + String.format("%.2f", lastAvg) + "," + lastGrade + "\n");
            JOptionPane.showMessageDialog(this, "Results saved to student_results.csv", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving results.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeCalculator app = new StudentGradeCalculator();
            app.setVisible(true);
        });
    }

    // Inner class for Subject Row
    class SubjectRow {
        JPanel panel;
        JTextField txtSubjectName;
        JTextField txtMark;
        JButton btnRemove;

        public SubjectRow(String defaultName) {
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            panel.setBackground(Color.WHITE);
            
            txtSubjectName = new JTextField(defaultName, 12);
            txtMark = new JTextField(5);
            txtMark.setHorizontalAlignment(JTextField.CENTER);
            
            btnRemove = new JButton("X");
            btnRemove.setBackground(new Color(231, 76, 60));
            btnRemove.setForeground(Color.WHITE);
            btnRemove.setMargin(new Insets(2, 5, 2, 5));
            btnRemove.addActionListener(e -> removeSubjectRow(this));
            
            panel.add(new JLabel("Subject:"));
            panel.add(txtSubjectName);
            panel.add(new JLabel("Marks (0-100):"));
            panel.add(txtMark);
            panel.add(btnRemove);
        }
    }
}