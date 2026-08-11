package CODSOFT.Task2_ATM_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ATMSystem extends JFrame {
    
    private Map<String, BankAccount> accounts;
    private BankAccount currentUser;
    private final String DATA_FILE = "accounts_data.csv";

    //UI Components ---
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    private JLabel lblBalanceDisplay;
    private JTextField txtAmount;
    private JLabel lblStatus;
    
    // Login UI
    private JTextField txtAccountNo;
    private JPasswordField txtPin;
    private JLabel lblLoginStatus;

    //Color Palette (Banking Theme) ---
    private final Color COLOR_PRIMARY = new Color(44, 62, 80);
    private final Color COLOR_ACCENT = new Color(52, 152, 219);
    private final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private final Color COLOR_WARNING = new Color(230, 126, 34);
    private final Color COLOR_BG = new Color(236, 240, 241);
       
    public ATMSystem() {
        loadData();
        
        // Window Setup
        setTitle("CodSoft ATM Interface - Production Ready");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createMainPanel(), "MAIN");
        
        add(cardPanel);
        cardLayout.show(cardPanel, "LOGIN");
    }
    
    // --- Data Persistence ---
    private void loadData() {
        accounts = new HashMap<>();
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // Create default accounts
            accounts.put("12345", new BankAccount("12345", "1234", 10000.00));
            accounts.put("98765", new BankAccount("98765", "9876", 5000.00));
            saveData();
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String accNo = parts[0];
                    String pin = parts[1];
                    double bal = Double.parseDouble(parts[2]);
                    BankAccount acc = new BankAccount(accNo, pin, bal);
                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        String[] txns = parts[3].split("\\|");
                        for (String t : txns) {
                            acc.addTransactionRecord(t);
                        }
                    }
                    accounts.put(accNo, acc);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    
    private void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (BankAccount acc : accounts.values()) {
                pw.println(acc.getAccountNumber() + "," + acc.getPin() + "," + acc.getBalance() + "," + acc.getTransactionsAsString());
            }
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // --- UI Creation ---
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PRIMARY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("SECURE LOGIN", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        
        JLabel lblAcc = new JLabel("Account No:");
        lblAcc.setForeground(Color.WHITE);
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(lblAcc, gbc);
        
        txtAccountNo = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtAccountNo, gbc);
        
        JLabel lblPin = new JLabel("PIN:");
        lblPin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPin, gbc);
        
        txtPin = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPin, gbc);
        
        JButton btnLogin = createStyledButton("LOGIN", COLOR_ACCENT);
        btnLogin.addActionListener(e -> handleLogin());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);
        
        lblLoginStatus = new JLabel("Enter credentials (e.g. 12345 / 1234)", SwingConstants.CENTER);
        lblLoginStatus.setForeground(new Color(189, 195, 199));
        gbc.gridy = 4;
        panel.add(lblLoginStatus, gbc);
        
        return panel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 1. TOP SECTION
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(3, 1));
        displayPanel.setBackground(COLOR_PRIMARY);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblBankTitle = new JLabel("SECURE BANKING SYSTEM");
        lblBankTitle.setForeground(new Color(189, 195, 199));
        lblBankTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        lblBalanceDisplay = new JLabel("₹ 0.00");
        lblBalanceDisplay.setForeground(Color.WHITE);
        lblBalanceDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        lblBalanceDisplay.setFont(new Font("SansSerif", Font.BOLD, 36));
        
        lblStatus = new JLabel("Welcome. Select a transaction.");
        lblStatus.setForeground(new Color(127, 140, 141));
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 12));

        displayPanel.add(lblBankTitle);
        displayPanel.add(lblBalanceDisplay);
        displayPanel.add(lblStatus);
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        
        // 2. CENTER SECTION
        JPanel centerContainer = new JPanel(new BorderLayout());
        
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(COLOR_BG);
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        JLabel lblEnterAmount = new JLabel("Enter Amount:");
        lblEnterAmount.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtAmount = new JTextField(15);
        txtAmount.setFont(new Font("Monospaced", Font.BOLD, 16));
        txtAmount.setHorizontalAlignment(JTextField.CENTER);
        txtAmount.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_PRIMARY));
        txtAmount.setBackground(COLOR_BG);
        inputPanel.add(lblEnterAmount);
        inputPanel.add(txtAmount);
        
        centerContainer.add(inputPanel, BorderLayout.NORTH);
        
        // 3. BOTTOM SECTION: Action Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton btnDeposit = createStyledButton("DEPOSIT", COLOR_SUCCESS);
        JButton btnWithdraw = createStyledButton("WITHDRAW", COLOR_WARNING);
        JButton btnCheckBal = createStyledButton("CHECK BALANCE", COLOR_ACCENT);
        JButton btnHistory = createStyledButton("TRANSACTIONS", new Color(155, 89, 182));
        JButton btnLogout = createStyledButton("LOGOUT", Color.GRAY);
        JButton btnExit = createStyledButton("EXIT", Color.DARK_GRAY);
        
        buttonPanel.add(btnDeposit);
        buttonPanel.add(btnWithdraw);
        buttonPanel.add(btnCheckBal);
        buttonPanel.add(btnHistory);
        buttonPanel.add(btnLogout);
        buttonPanel.add(btnExit);

        centerContainer.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(centerContainer, BorderLayout.CENTER);

        //Event Listeners
        btnDeposit.addActionListener(e -> handleDeposit());
        btnWithdraw.addActionListener(e -> handleWithdraw());
        btnCheckBal.addActionListener(e -> {
            lblStatus.setText("Balance Updated.");
            refreshBalance();
        });
        btnHistory.addActionListener(e -> showHistory());
        btnLogout.addActionListener(e -> handleLogout());
        btnExit.addActionListener(e -> System.exit(0));
        
        return mainPanel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(bg.darker());
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }
    
    // --- Logic Handlers ---
    
    private void handleLogin() {
        String accNo = txtAccountNo.getText().trim();
        String pin = new String(txtPin.getPassword()).trim();
        
        if (accounts.containsKey(accNo)) {
            BankAccount acc = accounts.get(accNo);
            if (acc.getPin().equals(pin)) {
                currentUser = acc;
                refreshBalance();
                txtAccountNo.setText("");
                txtPin.setText("");
                lblLoginStatus.setText("Enter credentials (e.g. 12345 / 1234)");
                lblLoginStatus.setForeground(new Color(189, 195, 199));
                cardLayout.show(cardPanel, "MAIN");
                return;
            }
        }
        lblLoginStatus.setText("Invalid Account Number or PIN!");
        lblLoginStatus.setForeground(Color.RED);
    }
    
    private void handleLogout() {
        currentUser = null;
        cardLayout.show(cardPanel, "LOGIN");
    }
    
    private void showHistory() {
        if (currentUser == null) return;
        List<String> txns = currentUser.getTransactionHistory();
        StringBuilder sb = new StringBuilder("Transaction History:\n\n");
        if (txns.isEmpty()) {
            sb.append("No transactions found.");
        } else {
            for (String t : txns) {
                sb.append(t).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Mini Statement", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleDeposit() {
        try {
            double amount = parseInput();
            currentUser.deposit(amount);
            saveData();
            refreshBalance();
            lblStatus.setText("Success: Deposited ₹" + amount);
            lblStatus.setForeground(new Color(39, 174, 96));
            clearInput();
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
        
    private void handleWithdraw() {
        try {
            double amount = parseInput();
            boolean success = currentUser.withdraw(amount);
            if (success) {
                saveData();
                refreshBalance();
                lblStatus.setText("Success: Withdrawn ₹" + amount);
                lblStatus.setForeground(new Color(211, 84, 0));
                clearInput();
            } else {
                showError("Insufficient Balance!");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private double parseInput() throws NumberFormatException {
        String text = txtAmount.getText();
        if (text.isEmpty()) throw new NumberFormatException();
        return Double.parseDouble(text);
    }

    private void refreshBalance() {
        if (currentUser != null) {
            lblBalanceDisplay.setText(String.format("₹%,.2f", currentUser.getBalance()));
        }
    }

    private void clearInput() {
        txtAmount.setText("");
    }

    private void showError(String msg) {
        lblStatus.setText(msg);
        lblStatus.setForeground(Color.RED);
        JOptionPane.showMessageDialog(this, msg, "Transaction Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMSystem().setVisible(true);
        });        
    }
}