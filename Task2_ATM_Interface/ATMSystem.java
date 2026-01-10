package CODSOFT.Task2_ATM_Interface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ATMSystem extends JFrame {
    
    //Business Logic Link ---
    private BankAccount userAccount;

    //UI Components ---
    private JLabel lblBalanceDisplay;
    private JTextField txtAmount;
    private JLabel lblStatus;

    //Color Palette (Banking Theme) ---
    private final Color COLOR_PRIMARY = new Color(44, 62, 80);    // Dark Navy
    private final Color COLOR_ACCENT = new Color(52, 152, 219);   // Blue
    private final Color COLOR_SUCCESS = new Color(46, 204, 113);  // Emerald Green
    private final Color COLOR_WARNING = new Color(230, 126, 34);  // Carrot Orange
    private final Color COLOR_BG = new Color(236, 240, 241);      // Soft Grey
       
    public ATMSystem() {
        // Initialize Account with default 10000.00
        userAccount = new BankAccount(10000.00);

        // Window Setup
        setTitle("CodSoft ATM Interface");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 1. TOP SECTION: Screen / Display
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(3, 1));
        displayPanel.setBackground(COLOR_PRIMARY);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblBankTitle = new JLabel("SECURE BANKING SYSTEM");
        lblBankTitle.setForeground(new Color(189, 195, 199)); // Light Grey
        lblBankTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        lblBalanceDisplay = new JLabel("₹ 10,000.00");
        lblBalanceDisplay.setForeground(Color.WHITE);
        lblBalanceDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        lblBalanceDisplay.setFont(new Font("SansSerif", Font.BOLD, 36));
        
        lblStatus = new JLabel("Welcome. Select a transaction.");
        lblStatus.setForeground(new Color(127, 140, 141)); // Muted Grey
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 12));

        displayPanel.add(lblBankTitle);
        displayPanel.add(lblBalanceDisplay);
        displayPanel.add(lblStatus);
        
        add(displayPanel, BorderLayout.NORTH);
        
        // 2. CENTER SECTION: Input Field
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

    }
}