package client;

import common.ConnectionService;
import common.Message;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;


// При создании клиента в конструктор передается
// экземпляр InetSocketAddress,
// который хранит IP сервера и порт.
public class Client  extends Thread{
    private Socket socket;
    private BufferedReader cin;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.cin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = cin.readLine();
                System.out.println(message);

            }
        } catch (SocketException e) {
            System.out.println("You left the chat-room");
        } catch (IOException exception) {
            System.out.println(exception);
        } finally {
            try {
                cin.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }



 }














