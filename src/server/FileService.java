package server;


import java.io.File;
import java.util.List;

public class FileService {

    File file;

    List<File> files;


    public File getFile(){ //получение файла  размером не более 2 Мб и описание не более 8 символов



        return  file;
    }


    public List<File> saveFileToList(File file){ //сохранение файла в список



        return files;
    }

    public void sendAllInfo(File file){// рассылаем инфу о новом файле всем пользователям



    }

    public void sendFileToClient(File file){//присылает список имен файлов, потом содержимое выбранного из списка файла.




    }






}
