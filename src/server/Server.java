package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Server extends Thread{

    private Socket socket;
    private ArrayList<Socket> clients;
    String filename;
    String description;
    private ConcurrentMap<Socket, UUID> clientNameList;

    public Server(Socket socket, ArrayList<Socket> clients, ConcurrentHashMap<Socket, UUID> clientNameList) {
        this.socket = socket;
        this.clients = clients;
        this.clientNameList = clientNameList;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            filename = "NoName.txt";
            description = "The file contains text";

            while (true) {
                String outputString = input.readLine();
                if (outputString.equals("disconnect")) {
                    throw new SocketException();
                }
                if (!clientNameList.containsKey(socket)) {
                    clientNameList.put(socket, UUID.randomUUID());
                } else if (outputString.equals("Message from other client:send")) {
                    ServerSocket serverSocket = new ServerSocket(8080);
                    // Принимаем подключение от клиента
                    Socket clientSocket = serverSocket.accept();
                    // Создаем новый поток для обработки запроса клиента
                    Thread thread = new FileServer(clientSocket,filename,description);
                    showMessageToAllClients(socket,"Download new file:"+filename +":"+ description);
                    thread.start();
                } else {
                    showMessageToAllClients(socket, outputString);
                }
                int activeThreads = Thread.activeCount();
                System.out.println("Количество потоков: " + activeThreads);
            }
        } catch (SocketException e) {
            String printMessage = clientNameList.get(socket) + " disconnect";
            showMessageToAllClients(socket, printMessage);
            clients.remove(socket);
            clientNameList.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private void showMessageToAllClients(Socket sender, String outputString) {
        Socket socket;
        PrintWriter printWriter;
        int i = 0;
        while (i < clients.size()) {
            socket = clients.get(i);
            i++;
            try {
                if (socket != sender) {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(outputString);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}








