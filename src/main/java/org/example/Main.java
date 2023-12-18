package org.example;

import org.example.taskOne.TextFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        String fileName = "file.txt";
        TextFile.run(fileName);
    }

}