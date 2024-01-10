package server;

import common.ConnectionService;
import common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {


    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Socket> clients = new ArrayList<>();
        ConcurrentHashMap<Socket, UUID> clientNameList = new ConcurrentHashMap<>();
        try (ServerSocket serversocket = new ServerSocket(2222)) {
            System.out.println("Server is started...");
            while (true) {
                Socket socket = serversocket.accept();
                clients.add(socket);
                Server ThreadServer = new Server(socket, clients,  clientNameList);
                ThreadServer.start();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
    }// Добавить возможность отправки списка имен файлов и в
//зависимости от выбранного файла отправить его содержимое.
