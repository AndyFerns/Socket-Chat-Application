package chat.client;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.List;

public class ChatGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField inputField;
    private PrintWriter out;

    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public ChatGUI(PrintWriter out) {
        this.out = out;

        setTitle("Chat App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Sidebar for online users ===
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userList.setSelectionBackground(new Color(88, 101, 242));
        userList.setSelectionForeground(Color.WHITE);
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(180, 0));
        add(userScrollPane, BorderLayout.WEST);

        // === Message area ===
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        add(messageScrollPane, BorderLayout.CENTER);

        // === Input area ===
        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setMargin(new Insets(10, 10, 10, 10));
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(_ -> {
            String msg = inputField.getText();
            if (!msg.trim().isEmpty()) {
                out.println(msg);  // Send to server
                inputField.setText("");
            }
        });

        // === Look & feel ===
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}
    }

    // Appends a new message to the chat area
    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    // Updates the sidebar with currently online users
    public void updateUserList(List<String> users) {
        SwingUtilities.invokeLater(() -> {
            userListModel.clear();
            for (String user : users) {
                userListModel.addElement(user);
            }
        });
    }
}
