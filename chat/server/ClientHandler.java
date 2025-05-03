package chat.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // NEW: First line is username
            this.username = in.readLine();

            ChatServer.broadcast("[" + TIME_FORMAT.format(LocalTime.now()) + "] " + username + " has joined the chat.");

            String line;
            while ((line = in.readLine()) != null) {
                String timestamp = "[" + TIME_FORMAT.format(LocalTime.now()) + "]";
                ChatServer.broadcast(timestamp + " " + username + ": " + line);
            }

        } catch (IOException e) {
            System.err.println("Connection error with client.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.clients.remove(this);
            ChatServer.broadcast("[" + TIME_FORMAT.format(LocalTime.now()) + "] " + username + " has left the chat.");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
