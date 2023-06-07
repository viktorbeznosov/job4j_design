package ru.job4j.main;

public class Main {
    public static String binary(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(num % 2 == 0 ? 0 : 1);
            sb.append((i + 1) % 8 == 0 ? " " : "");
            num /= 2;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        int hashCode = 1309552426;
        int hash = hashCode ^ (hashCode >>> 16);

        System.out.println(binary(hashCode));
        System.out.println(binary(hashCode >>> 16));
        System.out.println(binary(hash));
        System.out.println(hash);
        System.out.println(Integer.parseInt("01001110000011100110000100100100", 2));

        System.out.println(binary(15));
    }
}
