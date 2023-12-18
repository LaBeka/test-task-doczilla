package org.example.taskOne;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextFile {

    public static void run(String fileName){
        String rootFolder = "путь_к_корневой_папке";

        List<File> textFiles = findTextFiles(new File(rootFolder));
        Collections.sort(textFiles);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (File file : textFiles) {
                writer.write("\n\n# Файл: " + file.getAbsolutePath() + "\n\n");
                processFile(file, writer);
            }

            writer.close();
            System.out.println("Файлы объединены в " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<File> findTextFiles(File folder) {
        List<File> textFiles = new ArrayList<>();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    textFiles.addAll(findTextFiles(file));
                } else if (file.getName().toLowerCase().endsWith(".txt")) {
                    textFiles.add(file);
                }
            }
        }

        return textFiles;
    }

    private static void processFile(File file, BufferedWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains("require ‘")) {
                String requiredFilePath = line.substring(line.indexOf("require ‘") + 9, line.lastIndexOf("’"));
                File requiredFile = new File(file.getParentFile(), requiredFilePath);

                if (requiredFile.exists() && requiredFile.isFile()) {
                    writer.write("\n\n# Директива require: " + requiredFilePath + "\n\n");
                    processFile(requiredFile, writer);
                }
            } else {
                writer.write(line + "\n");
            }
        }

        reader.close();
    }
}
