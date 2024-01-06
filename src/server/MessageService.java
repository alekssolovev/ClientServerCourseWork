package server;

import common.ConnectionService;
import common.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MessageService implements Serializable {
    Message message;
    Socket socket;
    ConnectionService connectionService;

    ConcurrentMap<UUID, Socket> clients;
    int  port;

    public MessageService(Message message, Socket socket, ConcurrentMap<UUID, Socket> clients) {
        this.message = message;
        this.socket=socket;
        this.clients = clients;
    }

    public Message getMessage() throws IOException {// Получение сообщения от клиента
        //String textOfMessage = null;
        // throws IOException
        //serverSocket = new ServerSocket(port);
        try  {
            connectionService = new ConnectionService(socket);
           message =connectionService.readMessage();
            //textOfMessage = message.getText();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка подключение клиента");
        }

        return message;

    }

    public void sendAllClient() { //Отправка сообщшения всем клиентам


    }

    public synchronized void broadcastMessage(Message message, ConcurrentMap<UUID, Socket> clients ) throws IOException {
            for (Map.Entry<UUID, Socket> entry : clients.entrySet()) {
                Socket clientSocket = entry.getValue();
                connectionService = new ConnectionService(clientSocket);
                try {
                    connectionService.writeMessage(message);
                } catch (IOException e) {
                    // Обработка ошибок ввода-вывода при отправке сообщения
                    e.printStackTrace();
                }
            }
        }
}
    











