package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsCollector {
    private final List<Long> integers = new ArrayList<>();
    private final List<Double> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    private final List<String> integersStrings = new ArrayList<>();
    private final List<String> floatsStrings = new ArrayList<>();

    public void updateInteger(long val, String original) { integers.add(val); integersStrings.add(original); }
    public void updateFloat(double val, String original) { floats.add(val); floatsStrings.add(original); }
    public void updateString(String val) { strings.add(val); }

    public void print(boolean shortStats, boolean fullStats) {
        if (shortStats) {
            System.out.println("Краткая сстатистика:");
            System.out.printf("Integers: %d, Floats: %d, Strings: %d%n",
                    integers.size(), floats.size(), strings.size());
        }

        if (fullStats) {
            System.out.println("Полная статистика:");
            if (!integers.isEmpty()) {
                long min = Collections.min(integers);
                long max = Collections.max(integers);
                String intMinStr = integersStrings.get(integers.indexOf(min));
                String intMaxStr = integersStrings.get(integers.indexOf(max));
                long sum = integers.stream().mapToLong(Long::longValue).sum();
                double avg = sum / (double) integers.size();
                System.out.printf("Integers -> Количество=%d, Минимум=%s, Максимум=%s, Сумма=%d, Среднее=%.2f%n",
                        integers.size(), intMinStr, intMaxStr, sum, avg);
            }

            if (!floats.isEmpty()) {
                double min = Collections.min(floats);
                double max = Collections.max(floats);
                String floatsMinStr = floatsStrings.get(floats.indexOf(min));
                String floatsMaxStr = floatsStrings.get(floats.indexOf(max));
                double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
                double avg = sum / floats.size();
                System.out.printf("Floats -> Количество=%d, Минимум=%s, Максимум=%s, Сумма=%.5f, Среднее=%.5f%n",
                        floats.size(), floatsMinStr, floatsMaxStr, sum, avg);
            }

            if (!strings.isEmpty()) {
                int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
                int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
                System.out.printf("Strings -> Количество=%d, Минимальная длина=%d, Максимальная длина=%d%n",
                        strings.size(), minLen, maxLen);
            }
        }
    }
}
