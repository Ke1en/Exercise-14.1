
/*
Напишите программу, которая выполняет следующие шаги:
1. Создание файла:
Создайте файл с именем source.txt в текущей директории.
Запишите в этот файл некий текст
2. Копирование содержимого:
Прочитайте содержимое файла source.txt.
Создайте новый файл с именем copy.txt в той же директории.
Скопируйте содержимое из source.txt в copy.txt.
3. Перемещение файла:
Создайте директорию с именем newDir в текущей директории.
Переместите файл source.txt в директорию newDir.
4. Проверка перемещения и копирования:
После выполнения всех операций проверьте, что:
Файл source.txt находится в newDir.
Файл copy.txt содержит текст из source.txt.
Могут понадобиться такие классы как File, FileReader, FileWriter*/

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем файл и записываем некий текст
            // При повторном запуске программы файл source будет находиться в корневой папке, а также в newDIR
            String data = "Некий текст";
            FileWriter fw = new FileWriter("source.txt");
            fw.write(data);
            fw.close();

            // Читаем содержимое файла source
            FileReader fr = new FileReader("source.txt");
            // Записываем в файл copy содержимое source
            try (FileWriter fr2 = new FileWriter("copy.txt")) {
                int writeCopy;
                while ((writeCopy = fr.read()) != -1) {
                    fr2.write(writeCopy);
                }
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Создаем новую директорию
            File newDIR = new File("newDIR/");
            if (!newDIR.exists()) {
                newDIR.mkdirs();
            } else {
                System.out.println("Директория существует");
            }

            // Копируем в новую директорию файл source
            Path sourceFilePath = Paths.get("source.txt");
            Path destinationFilePath = Paths.get("newDIR/source.txt");
            try {
                if (Files.exists(destinationFilePath)) {
                    System.out.println("Файл source находится в newDIR");
                    Files.delete(sourceFilePath);
                } else {
                    Files.move(sourceFilePath, destinationFilePath);
                    System.out.println("Файл перемещен");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Проверка на одинаковое содержимое
            Path copyFilePath = Paths.get("copy.txt");
            if (Files.mismatch(copyFilePath, destinationFilePath) == -1) {
                System.out.println("Содержимое одинаковое");
            } else {
                System.out.println("Содержимое разное");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}