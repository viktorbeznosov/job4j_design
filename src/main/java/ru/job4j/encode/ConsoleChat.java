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
    private List<String> phrases;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
        this.phrases = readPhrases();
    }

    public void run() {
        boolean stop = false;
        Scanner in = new Scanner(System.in);
        String line = "";
        List<String> log = new ArrayList<>();
        Random random = new Random();
        while (!OUT.equals(line)) {
            line = in.nextLine();
            log.add(line);
            if (!stop && STOP.equals(line) || stop && CONTINUE.equals(line)) {
                stop = !stop;
            }
            if (!stop) {
                System.out.println(phrases.get(random.nextInt(phrases.size())));
            }
        }
        saveLog(log);
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
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
            for (String str: log) {
                pw.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("data/chat-log.txt", "data/answers.txt");
        cc.run();
    }
}
