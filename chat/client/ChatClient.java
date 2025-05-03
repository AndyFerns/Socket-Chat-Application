package chat.client;

import chat.client.ChatGUI;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Prompt for username
            String username = JOptionPane.showInputDialog("Enter your username:");
            if (username == null || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty.");
                socket.close();
                return;
            }
            out.println(username);

            ChatGUI gui = new ChatGUI(out);
            gui.setVisible(true);

            // Read messages from server
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("[USERLIST]")) {
                    // Parse user list and update sidebar
                    String csv = line.substring(11); // after [USERLIST]
                    List<String> users = Arrays.asList(csv.split(","));
                    gui.updateUserList(users);
                } else {
                    gui.appendMessage(line); // Regular chat message
                }
            }

            socket.close();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
