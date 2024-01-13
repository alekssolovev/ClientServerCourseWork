package server;

import common.ConnectionService;
import common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

import java.util.UUID;


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
    }
