package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class FileServer extends Thread {
    private Socket clientSocket;
    String filename;
    String description;
    private final int MAX_FILENAME_LENGTH = 11;
    private final long MAX_FILE_SIZE = 10_000; // 1 MB
    private ConcurrentMap<String, String> fileStorage = new ConcurrentHashMap<>();

    public FileServer(Socket socket,String filename,String description) {
        clientSocket = socket;
        this.filename = filename;
        this.description = description;
    }

    public void run() {
        try {
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Создаем поток для чтения данных от клиента
            InputStream inputStream = clientSocket.getInputStream();

            // Получаем имя файла от клиента
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             filename = reader.readLine();
             description = reader.readLine();


            // Проверяем длину имени файла
            if (filename.length() > MAX_FILENAME_LENGTH) {
                System.out.println("File name is too long");
                return;
            }

            System.out.println("Received file: " + filename);
            System.out.println("Description:"+ description);

            // Создаем поток для записи файла в память
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Читаем данные из потока от клиента и записываем в память
            byte[] buffer = new byte[4096];
            int bytesRead;
            long fileSize = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                fileSize += bytesRead;

                // Проверяем размер файла
                if (fileSize > MAX_FILE_SIZE) {
                    System.out.println("File size exceeds the limit");
                    outputStream.close();
                    inputStream.close();
                    clientSocket.close();
                    return;
                }
            }

            // Сохраняем файл в коллекции
            fileStorage.put(filename, description);

            // Закрываем потоки и сокеты
            outputStream.close();
            inputStream.close();
            clientSocket.close();

            System.out.println("File saved: " + filename);
            System.out.println(fileStorage.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


