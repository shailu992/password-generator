import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import javax.swing.*;

public class PasswordGenerator extends JFrame {
    private JTextField lengthField;
    private JTextArea resultArea;
    private JButton generateButton;
    private JButton copyButton;
    private JCheckBox includeUppercase;
    private JCheckBox includeLowercase;
    private JCheckBox includeDigits;
    private JCheckBox includeSpecial;

    public PasswordGenerator() {
        setTitle("Secure Password Generator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 1)); // Increased rows for spacing
        panel.setBackground(new Color(0, 33, 71)); // Oxford Blue

        JLabel titleLabel = new JLabel("Strong Password Generator", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        JLabel lengthLabel = new JLabel("Enter Password Length:", SwingConstants.CENTER);
        lengthLabel.setForeground(Color.WHITE); // White text
        lengthLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lengthLabel);

        lengthField = new JTextField();
        panel.add(lengthField);

        JLabel customizeLabel = new JLabel("Customize Your Password", SwingConstants.CENTER);
        customizeLabel.setForeground(Color.WHITE);
        customizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(customizeLabel);

        includeUppercase = new JCheckBox("Include Uppercase Letters (A-Z)", false);
        includeLowercase = new JCheckBox("Include Lowercase Letters (a-z)", false);
        includeDigits = new JCheckBox("Include Digits (0-9)", false);
        includeSpecial = new JCheckBox("Include Special Characters (!@#$%^&*...)", false);

        includeUppercase.setBackground(new Color(0, 33, 71)); // Oxford Blue
        includeLowercase.setBackground(new Color(0, 33, 71)); // Oxford Blue
        includeDigits.setBackground(new Color(0, 33, 71)); // Oxford Blue
        includeSpecial.setBackground(new Color(0, 33, 71)); // Oxford Blue

        includeUppercase.setForeground(Color.WHITE);
        includeLowercase.setForeground(Color.WHITE);
        includeDigits.setForeground(Color.WHITE);
        includeSpecial.setForeground(Color.WHITE);

        panel.add(includeUppercase);
        panel.add(includeLowercase);
        panel.add(includeDigits);
        panel.add(includeSpecial);
        panel.add(Box.createVerticalStrut(5));

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultArea = new JTextArea(1, 15);
        resultArea.setEditable(false);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        copyButton = new JButton("Copy");
        copyButton.setBackground(new Color(0, 255, 255)); // Cyan
        copyButton.setPreferredSize(new Dimension(80, 15)); // Small size
        resultPanel.add(copyButton, BorderLayout.EAST);

        panel.add(resultPanel);

        panel.add(Box.createVerticalStrut(10)); // Adding space before button

        generateButton = new JButton("Generate Password");
        generateButton.setBackground(new Color(0, 255, 255)); // Cyan
        generateButton.setPreferredSize(new Dimension(90, 20)); // Decreased size
        panel.add(generateButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });

        add(panel);
    }

    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText().trim());
            if (length <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/";

        StringBuilder characterPool = new StringBuilder();
        if (includeUppercase.isSelected()) characterPool.append(upper);
        if (includeLowercase.isSelected()) characterPool.append(lower);
        if (includeDigits.isSelected()) characterPool.append(digits);
        if (includeSpecial.isSelected()) characterPool.append(special);

        if (characterPool.length() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one character set.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        resultArea.setText(password.toString());
    }

    private void copyToClipboard() {
        String password = resultArea.getText();
        if (!password.isEmpty()) {
            StringSelection selection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordGenerator generator = new PasswordGenerator();
            generator.setVisible(true);
        });
    }
}