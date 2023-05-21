package ru.job4j.generics;

import java.util.Date;

public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public static void main(String[] args) {
        Node node = new Node<>(new Person("Person", 19, new Date(913716000000L)), null);
        Node next = new Node<>("Person", node);
        System.out.println(next.getData());
    }
}