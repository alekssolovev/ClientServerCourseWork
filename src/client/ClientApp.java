package client;




import common.ConnectionService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        String reply = "empty";
        String filename;
        String description;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your message: ");


        try (Socket socket = new Socket("localhost", 2222)) {
            PrintWriter cout = new PrintWriter(socket.getOutputStream(), true);


            Client threadClient = new Client(socket);
            new Thread(threadClient).start(); // start thread to receive message

            while (true){
                String message = ("Message from other client:");
                reply = sc.nextLine();
                if (reply.equals("exit")) {
                    cout.println("exit");
                    break;
                }
                if(reply.equals("send")){
                    FileClient client = new FileClient("localhost",8080,"send");
                    filename = "NoName.txt";
                    description = "The file contains text";
                    cout.println(filename);
                    cout.println(description);
                    client.start();
                }
                cout.println(message + reply);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }//Добавить возможность запросить у сервера список имен файлов и
    //получить описание выбранного файла.

}



