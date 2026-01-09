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
    }
}