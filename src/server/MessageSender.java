package server;

import common.ConnectionService;
import common.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MessageSender {
    ConcurrentMap<UUID, Socket> clients;
    String message;
    ConnectionService connectionService;
    Message message1;

    Socket socket;


    public MessageSender(Message message1, Socket socket) {
        this.message1 = message1;
        this.socket = socket;
    }

    public void sendToAllClients(ConcurrentMap<UUID, Socket> clients, Message message) throws IOException {
        connectionService = new ConnectionService(socket);
        for (Map.Entry<UUID, Socket> entry : clients.entrySet()) {
            Socket socket = entry.getValue();
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                connectionService.writeMessage(message1);
            } catch (IOException e) {
                // Обработка ошибок при отправке сообщения
                e.printStackTrace();
            }
        }
    }
}
