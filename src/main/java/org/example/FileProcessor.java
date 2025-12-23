package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileProcessor {
    private final ResultWriter writer;
    private final StatisticsCollector stats;
    private final boolean collectStats;

    public FileProcessor(String outputDir, String prefix, boolean append, boolean collectStats) throws IOException {
        this.writer = new ResultWriter(outputDir, prefix, append);
        this.collectStats = collectStats;
        this.stats = collectStats ? new StatisticsCollector() : null;
    }

    public void processFiles(List<String> files) throws IOException {
        for (String file : files) {
            try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    classifyAndWrite(line.trim());
                }
            }
        }
        writer.closeAll();
    }

    private void classifyAndWrite(String value) throws IOException {
        if (value.isEmpty()) return;

        try {
            long l = Long.parseLong(value);
            writer.writeInteger(value);
            if (collectStats) stats.updateInteger(l, value);
            return;
        } catch (NumberFormatException ignored) {}

        try {
            double d = Double.parseDouble(value);
            if (d == Math.rint(d) && d >= Long.MIN_VALUE && d <= Long.MAX_VALUE) {
                writer.writeInteger(value);
                if (collectStats) stats.updateInteger((long) d, value);
            } else {
                writer.writeFloat(value);
                if (collectStats) stats.updateFloat(d, value);
            }
            return;
        } catch (NumberFormatException ignored) {}

        writer.writeString(value);
        if (collectStats) stats.updateString(value);
    }

    public StatisticsCollector getStats() {
        return stats;
    }
}