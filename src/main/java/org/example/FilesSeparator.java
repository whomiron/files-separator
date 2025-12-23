package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FilesSeparator {

    public static void main(String[] args) {
        new FilesSeparator().run(args);
    }

    private void run(String[] args) {
        String outputDir = ".";
        String prefix = "";
        boolean append = false;
        boolean shortStats = false;
        boolean fullStats = false;

        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> outputDir = args[++i];
                case "-p" -> prefix = args[++i];
                case "-a" -> append = true;
                case "-s" -> shortStats = true;
                case "-f" -> fullStats = true;
                default ->   inputFiles.add(args[i]);
            }
        }

        if (inputFiles.isEmpty()) {
            System.err.println("Не указаны входные файлы");
            return;
        }

        try {
            FileProcessor processor = new FileProcessor(outputDir, prefix, append, shortStats || fullStats);
            processor.processFiles(inputFiles);

            if (shortStats || fullStats) {
                StatisticsCollector stats = processor.getStats();
                stats.print(shortStats, fullStats);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
