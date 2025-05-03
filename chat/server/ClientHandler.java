package chat.server;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Setup I/O
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get username
            username = in.readLine();
            synchronized (ChatServer.clients) {
                ChatServer.clients.add(this);
                broadcastUserList(); // Send updated user list
            }

            ChatServer.broadcast("[Server] " + username + " has joined the chat.");

            // Message handling loop
            String message;
            while ((message = in.readLine()) != null) {
                ChatServer.broadcast("[" + username + "] " + message);
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected unexpectedly.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized (ChatServer.clients) {
                ChatServer.clients.remove(this);
                ChatServer.broadcast("[Server] " + username + " has left the chat.");
                broadcastUserList(); // Update list after user leaves
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUsername() {
        return username;
    }

    // Broadcasts user list to all clients
    private void broadcastUserList() {
        StringBuilder list = new StringBuilder("[USERLIST]");
        for (ClientHandler client : ChatServer.clients) {
            list.append(client.getUsername()).append(",");
        }
        // Remove trailing comma
        if (list.charAt(list.length() - 1) == ',') {
            list.setLength(list.length() - 1);
        }

        for (ClientHandler client : ChatServer.clients) {
            client.sendMessage(list.toString());
        }
    }
}
