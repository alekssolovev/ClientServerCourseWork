package client;

import java.io.*;

import java.net.*;

public class FileClient extends Thread {
    private String hostname;
    private int port;

    public FileClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        try {
            // Создаем сокет и устанавливаем соединение с сервером
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to server: " + hostname);

            // Получаем поток для отправки данных на сервер
            OutputStream outputStream = socket.getOutputStream();

            // Создаем поток для чтения файла на клиенте
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Sani4\\IdeaProjects\\files\\ClientServerCourseWork\\src\\client\\NoName.txt");

            // Отправляем имя файла на сервер
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println("NoName.txt");

            // Читаем данные из файла и отправляем на сервер
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Закрываем потоки и сокет
            fileInputStream.close();
            outputStream.close();
            socket.close();

            System.out.println("File sent successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}