package client;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ClientApp {


    public static void main(String[] args) throws IOException, InterruptedException {

        String reply = "empty";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your message: ");
        UUID name = UUID.randomUUID();

        try (Socket socket = new Socket("localhost", 2222)) {
            PrintWriter cout = new PrintWriter(socket.getOutputStream(), true);

            Client threadClient = new Client(socket);
            new Thread(threadClient).start(); // start thread to receive message

            do {
                String message = ("ID " + name + " : ");
                reply = sc.nextLine();
                if (reply.equals("exit")) {
                    cout.println("exit");
                    break;
                }
                cout.println(message + reply);
            } while (!reply.equals("exit"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    }


