package chat.client;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField inputField;
    private PrintWriter out;

    public ChatGUI(PrintWriter out) {
        this.out = out;

        setTitle("Java Chat");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        // Sending user message
        inputField.addActionListener(_ -> {
            String msg = inputField.getText();
            if (!msg.trim().isEmpty()) {
                out.println(msg);  // Send to server (server adds timestamp and username)
                inputField.setText("");
            }
        });
    }

    // NEW: Appends message to GUI
    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }
}
