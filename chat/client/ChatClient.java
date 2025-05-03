package chat.client;

import chat.client.ChatGUI;

import java.io.*;
import javax.swing.JOptionPane;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // NEW: Ask for username
            String username = JOptionPane.showInputDialog("Enter your username:");
            out.println(username);  // Send username to server

            ChatGUI gui = new ChatGUI(out);
            gui.setVisible(true);

            String line;
            while ((line = in.readLine()) != null) {
                gui.appendMessage(line); // Display server messages with timestamp + username
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
