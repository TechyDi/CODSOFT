package CODSOFT.Task3_Student_Management_Sys;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement extends JFrame {

    //Data Storage ---
    private List<Student> studentList;
    private final String FILE_NAME = "students.dat";

    //GUI Components ---
    private JTextField txtName, txtRoll, txtGrade, txtEmail, txtSearch;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    //Colors ---
    private final Color COL_PRIMARY = new Color(55, 71, 133); // Indigo (Header)
    private final Color COL_BG = new Color(245, 245, 250);    // Soft White
    private final Color COL_PLACEHOLDER = Color.GRAY;
    private final Color COL_TEXT = Color.BLACK;
    private final Color COL_HIGHLIGHT = new Color(255, 235, 59); // Highlighter Yellow
    
    // Action Colors
    private final Color COL_ADD = new Color(46, 204, 113);    // Green
    private final Color COL_UPDATE = new Color(243, 156, 18); // Orange
    private final Color COL_DELETE = new Color(231, 76, 60);  // Red
    private final Color COL_SHOW_ALL = new Color(52, 152, 219); // Blue for Show All

    //State Variables ---
    private String currentSearchQuery = ""; // Stores text for highlighting logic

    public StudentManagement() {
        // 1. Initialize System
        loadData(); // Load data from file to memory, BUT DO NOT DISPLAY IT YET
        
        // 2. Window Setup
        setTitle("CodSoft Student Management System");
        setSize(1000, 700); 
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COL_BG);

        // 3. Header Panel (With Search Bar)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COL_PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Title (Center)
        JLabel titleLabel = new JLabel("Student Management Portal", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Search Bar (Right Side)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false); 

        txtSearch = new JTextField(15);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setPlaceholderLogic(txtSearch, "Search Name/Roll"); 

        // Live Search Listener
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { performSearch(); }
            public void removeUpdate(DocumentEvent e) { performSearch(); }
            public void changedUpdate(DocumentEvent e) { performSearch(); }
        });

        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setForeground(COL_PRIMARY);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> performSearch());

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 4. Input Panel (Left Side)
        JPanel mainInputPanel = new JPanel(new BorderLayout()); 
        mainInputPanel.setBackground(COL_BG);
        mainInputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        
        // 4.1 Form Fields Container
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Initialize Fields
        txtRoll = createFieldWithPlaceholder("Enter Roll No");
        txtName = createFieldWithPlaceholder("Enter Full Name");
        txtGrade = createFieldWithPlaceholder("e.g. A, B, 90%");
        txtEmail = createFieldWithPlaceholder("example@mail.com");

        // Add fields
        int gridY = 0;
        addFormItem(formPanel, new JLabel("Roll Number (ID):"), txtRoll, gbc, gridY++);
        addFormItem(formPanel, new JLabel("Full Name:"), txtName, gbc, gridY++);
        addFormItem(formPanel, new JLabel("Grade:"), txtGrade, gbc, gridY++);
        addFormItem(formPanel, new JLabel("Email (Optional):"), txtEmail, gbc, gridY++);

        mainInputPanel.add(formPanel, BorderLayout.NORTH);

        // 4.2 Button Container
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10)); 
        buttonPanel.setBackground(COL_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnAdd = createStyledButton("Add Student", COL_ADD);
        JButton btnUpdate = createStyledButton("Update Existing", COL_UPDATE);
        JButton btnDelete = createStyledButton("Delete Student", COL_DELETE);
        JButton btnShowAll = createStyledButton("Show All Students", COL_SHOW_ALL); 
        JButton btnClear = createStyledButton("Clear Form", Color.GRAY);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnShowAll); 
        buttonPanel.add(btnClear);

        // Wrapper
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(COL_BG);
        buttonWrapper.add(buttonPanel, BorderLayout.NORTH);
        
        mainInputPanel.add(buttonWrapper, BorderLayout.CENTER);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainInputPanel);
        scrollPane.setBorder(null); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(320, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.WEST);

        // 5. Table Area (Center)
        String[] columns = {"Roll No", "Name", "Grade", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.getTableHeader().setBackground(COL_PRIMARY);
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // APPLY CUSTOM RENDERER FOR HIGHLIGHTING
        HighlightRenderer renderer = new HighlightRenderer();
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    setFieldValue(txtRoll, tableModel.getValueAt(selectedRow, 0).toString());
                    setFieldValue(txtName, tableModel.getValueAt(selectedRow, 1).toString());
                    setFieldValue(txtGrade, tableModel.getValueAt(selectedRow, 2).toString());
                    setFieldValue(txtEmail, tableModel.getValueAt(selectedRow, 3).toString());
                    txtRoll.setEditable(false);
                }
            }
        });

        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        //Event Listeners ---
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnShowAll.addActionListener(e -> showAllStudents()); 
        btnClear.addActionListener(e -> clearForm());
        
        SwingUtilities.invokeLater(() -> headerPanel.requestFocusInWindow());
    }

    //INNER CLASS: Custom Renderer for Highlighting ---
    class HighlightRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String cellText = value.toString().toLowerCase();
            String query = currentSearchQuery.toLowerCase();

            // Logic: If search query exists AND cell text contains it -> Highlight Yellow
            if (!query.isEmpty() && cellText.contains(query)) {
                c.setBackground(COL_HIGHLIGHT);
                c.setForeground(Color.BLACK); // Ensure text is readable on yellow
            } else {
                // Restore default colors
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
            }
            return c;
        }
    }

    //Helper Methods ---

    private void addFormItem(JPanel panel, JLabel label, JTextField field, GridBagConstraints gbc, int logicalRow) {
        gbc.gridy = logicalRow * 2; 
        panel.add(label, gbc);
        gbc.gridy = (logicalRow * 2) + 1; 
        field.setPreferredSize(new Dimension(0, 35)); 
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 45)); 
        return btn;
    }
    
    //PLACEHOLDER LOGIC ---
    
    private JTextField createFieldWithPlaceholder(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setForeground(COL_PLACEHOLDER);
        setPlaceholderLogic(field, placeholder);
        return field;
    }

    private void setPlaceholderLogic(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(COL_TEXT);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(COL_PLACEHOLDER);
                }
            }
        });
    }
    
    private String getInputValue(JTextField field, String placeholder) {
        String text = field.getText().trim();
        if (text.equals(placeholder)) return "";
        return text;
    }
    
    private void setFieldValue(JTextField field, String value) {
        field.setText(value);
        field.setForeground(COL_TEXT);
    }
    
    private void resetField(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(COL_PLACEHOLDER);
    }

    //CORE LOGIC METHODS ---
    
    private void performSearch() {
        // Update global query for highlighting logic
        currentSearchQuery = getInputValue(txtSearch, "Search Name/Roll");
        String queryLower = currentSearchQuery.toLowerCase();
        
        if (queryLower.isEmpty()) {
            showAllStudents(); 
            return;
        }

        List<Student> searchResults = new ArrayList<>();
        boolean found = false;

        for (Student s : studentList) {
            // Check Roll No, Name, Grade, Email
            if (String.valueOf(s.getRollNumber()).contains(queryLower) || 
                s.getName().toLowerCase().contains(queryLower) ||
                s.getGrade().toLowerCase().contains(queryLower) ||
                s.getEmail().toLowerCase().contains(queryLower)) {
                searchResults.add(s);
                found = true;
            }
        }

        if (found) {
            refreshTable(searchResults); 
        } else {
            // If no match, clear table to indicate 0 results
            tableModel.setRowCount(0);
        }
    }

    private void showAllStudents() {
        currentSearchQuery = ""; // Clear highlighting
        if (studentList.isEmpty()) {
            showError("Database is empty! Add a student first.");
        } else {
            refreshTable(studentList); 
        }
    }

    // Feature 2: Grade Validation Logic
    private String validateAndFormatGrade(String rawGrade) {
        String grade = rawGrade.toUpperCase(); // Normalize case (a -> A)
        
        // Regex: Must be exactly one character, A-F
        if (!grade.matches("[A-F]")) {
            showError("Invalid Grade! Must be a single letter (A-F).");
            return null;
        }
        return grade;
    }

    private void addStudent() {
        String name = getInputValue(txtName, "Enter Full Name");
        String rollStr = getInputValue(txtRoll, "Enter Roll No");
        String rawGrade = getInputValue(txtGrade, "e.g. A, B, 90%");
        String email = getInputValue(txtEmail, "example@mail.com");

        if (name.isEmpty() || rollStr.isEmpty() || rawGrade.isEmpty()) {
            showError("Please fill all required fields!"); return;
        }

        // Validate Grade
        String grade = validateAndFormatGrade(rawGrade);
        if (grade == null) return; // Stop if invalid

        try {
            int roll = Integer.parseInt(rollStr);
            if (isRollUnique(roll)) {
                studentList.add(new Student(name, roll, grade, email));
                saveData(); 
                refreshTable(studentList); 
                clearForm();
                showSuccess("Student added successfully!");
            } else showError("Roll Number already exists!");
        } catch (NumberFormatException e) { showError("Roll Number must be numeric!"); }
    }

    private void updateStudent() {
        String rollStr = getInputValue(txtRoll, "Enter Roll No");
        String rawGrade = getInputValue(txtGrade, "e.g. A, B, 90%");
        
        if (rollStr.isEmpty()) { showError("Select a student first."); return; }
        
        // Validate Grade
        String grade = validateAndFormatGrade(rawGrade);
        if (grade == null) return; // Stop if invalid

        int roll = Integer.parseInt(rollStr);
        for (Student s : studentList) {
            if (s.getRollNumber() == roll) {
                s.setName(getInputValue(txtName, "Enter Full Name")); 
                s.setGrade(grade); 
                s.setEmail(getInputValue(txtEmail, "example@mail.com"));
                saveData(); 
                refreshTable(studentList); 
                clearForm(); 
                showSuccess("Updated successfully!"); 
                return;
            }
        }
    }

    private void deleteStudent() {
        String rollStr = getInputValue(txtRoll, "Enter Roll No");
        if (rollStr.isEmpty()) return;
        if (JOptionPane.showConfirmDialog(this, "Delete this student?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            studentList.removeIf(s -> s.getRollNumber() == Integer.parseInt(rollStr));
            saveData(); 
            refreshTable(studentList); 
            clearForm(); 
            showSuccess("Student deleted.");
        }
    }

    private boolean isRollUnique(int roll) {
        return studentList.stream().noneMatch(s -> s.getRollNumber() == roll);
    }

    private void refreshTable(List<Student> dataToShow) {
        tableModel.setRowCount(0);
        for (Student s : dataToShow) {
            tableModel.addRow(new Object[]{s.getRollNumber(), s.getName(), s.getGrade(), s.getEmail()});
        }
    }

    private void clearForm() {
        resetField(txtName, "Enter Full Name");
        resetField(txtRoll, "Enter Roll No");
        resetField(txtGrade, "e.g. A, B, 90%");
        resetField(txtEmail, "example@mail.com");
        txtRoll.setEditable(true); studentTable.clearSelection();
        getContentPane().requestFocusInWindow();
    }

    private void showSuccess(String msg) { JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }

    //File Handling ---
    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentList = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) { studentList = new ArrayList<>(); }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) { showError("Failed to save data!"); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagement().setVisible(true));
    }
}