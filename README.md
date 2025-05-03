# Java Socket Chat Application 💬

A simple yet modern real-time chat application built in Java using sockets and Swing for GUI. Inspired by Discord’s clean layout, this project demonstrates client-server communication with a stylish dark-themed interface.

## ✨ Features

- 📡 Real-time messaging over TCP sockets
- 👤 Username-based login (displays all active users)
- 🧍 Sidebar showing online users
- 🪟 Clean Swing-based GUI

## 📂 Project Structure

chat/
├── client/
│ ├── ChatClient.java
│ └── ChatGUI.java
├── server/
│ └── ChatServer.java
| └── ChatHandler.java

## 🛠️ Requirements

- Java 8 or higher
- A terminal or IDE that supports Java compilation and execution (duh)

## 🚀 How to Run

### 1. Compile all files

```bash
javac chat/server/ChatServer.java chat/client/ChatClient.java chat/client/ChatGUI.java
```

### 2. Start the server

```bash
java chat.server.ChatServer
```

### 3. Start clients (in separate terminals or IDE Windows)

```bash
java chat.client.ChatClient
```

## Concepts covered

- Java Socket Programming (TCP/IP)
- Multi-Threading for handling multiple clients at the same time
- GUI design with Swing
- Message broadcasting and client management
