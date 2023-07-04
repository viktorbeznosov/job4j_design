package ru.job4j.io;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Analysis {

    private boolean alarm;
    private String startPeriod;
    private String endPeriod;

    private List<String> readFromSource(String source) {
        List<String> intervals = new LinkedList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            alarm = false;
            List<String> alarmStatuses = List.of("400", "500");
            in.lines()
                    .forEach(line -> {
                        String[] lineItems = line.split(" ");
                        String currentStatus = lineItems[0];
                        if (!alarm && alarmStatuses.contains(currentStatus)) {
                            startPeriod = lineItems[1];
                            alarm = true;
                        }
                        if (alarm && !alarmStatuses.contains(currentStatus)) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(startPeriod).append(";").append(lineItems[1]).append(";");
                            intervals.add(builder.toString());
                            alarm = false;
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return intervals;
    }

    private void writeToTarget(List<String> intervals, String target) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            intervals.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unavailable(String source, String target) {
        writeToTarget(readFromSource(source), target);
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}