package CODSOFT.Task1_Grade_Calculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculator extends JFrame {

    //Component Declaration ---
    private JTextField[] marksInputs;
    private String[] subjects = {"Physics", "Chemistry", "Maths", "English", "Computer Science"};
    private JLabel lblTotalDisplay, lblAvgDisplay, lblGradeDisplay;
    private JButton btnCompute, btnClear;

    //Constructor to Initialize GUI ---
    public StudentGradeCalculator() {
        // Window Settings
        setTitle("CodSoft Internship | Grade Calculator");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Using BorderLayout for better organization
        getContentPane().setBackground(new Color(245, 245, 250)); // Light distinct background

        // 1. Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        JLabel headerTitle = new JLabel("Student Assessment Portal");
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerTitle);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Input Section (Center)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(subjects.length, 2, 10, 15));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        inputPanel.setOpaque(false);

        marksInputs = new JTextField[subjects.length];

        for (int i = 0; i < subjects.length; i++) {
            JLabel lblSubject = new JLabel(subjects[i] + ":");
            lblSubject.setFont(new Font("SansSerif", Font.PLAIN, 14));
            
            marksInputs[i] = new JTextField();
            marksInputs[i].setFont(new Font("Monospaced", Font.BOLD, 14));
            marksInputs[i].setHorizontalAlignment(JTextField.CENTER);

            inputPanel.add(lblSubject);
            inputPanel.add(marksInputs[i]);
        }
        add(inputPanel, BorderLayout.CENTER);
        
        // 3. Results & Action Section (South)
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayout(4, 1, 5, 5));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        footerPanel.setOpaque(false);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        btnCompute = new JButton("Calculate Grade");
        btnClear = new JButton("Clear Form");

        // Styling Buttons
        styleButton(btnCompute, new Color(46, 204, 113)); // Emerald Green
        styleButton(btnClear, new Color(231, 76, 60));    // Alizarin Red
        
        buttonPanel.add(btnCompute);
        buttonPanel.add(btnClear);

        // Result Labels
        lblTotalDisplay = createResultLabel("Total Marks: 0 / 500");
        lblAvgDisplay = createResultLabel("Average Percentage: 0.0%");
        lblGradeDisplay = createResultLabel("Grade: N/A");
        lblGradeDisplay.setFont(new Font("Arial", Font.BOLD, 16));

        footerPanel.add(buttonPanel);
        footerPanel.add(lblTotalDisplay);
        footerPanel.add(lblAvgDisplay);
        footerPanel.add(lblGradeDisplay);

        add(footerPanel, BorderLayout.SOUTH);

        //Event Listeners ---
        btnCompute.addActionListener(e -> performCalculation());
        btnClear.addActionListener(e -> clearForm());
    }
    
    //Helper Method to Style Buttons ---
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    //Helper Method for Result Labels ---
    private JLabel createResultLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Verdana", Font.PLAIN, 14));
        return lbl;
    }
