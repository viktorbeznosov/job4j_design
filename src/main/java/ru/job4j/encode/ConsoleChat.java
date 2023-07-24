package ru.job4j.encode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        boolean stop = false;
        Scanner in = new Scanner(System.in);
        String line = "";
        List<String> log = new ArrayList<>();
        while (!line.equals(OUT)) {
            line = in.nextLine();
            log.add(line);
            if (!stop && STOP.equals(line) || stop && CONTINUE.equals(line)) {
                stop = !stop;
            }
            if (!stop) {
                System.out.println(getRandomPhrase());
            }
        }
        saveLog(log);
    }

    private String getRandomPhrase() {
        List<String> phrases = readPhrases();
        Random random = new Random();
        return phrases.get(random.nextInt(phrases.size()));
    }

    private List<String> readPhrases() {
        List<String> phrases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers))) {
            br.lines().forEach(phrases::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phrases;
    }

    private void saveLog(List<String> log) {
        StringBuilder builder = new StringBuilder();
        for (String str: log) {
            builder.append(str).append(System.lineSeparator());
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
            pw.println(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("data/chat-log.txt", "data/answers.txt");
        cc.run();
    }
}
