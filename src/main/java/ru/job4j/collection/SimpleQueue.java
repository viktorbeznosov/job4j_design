package ru.job4j.collection;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    private int sizeIn;
    private int sizeOut;

    public T poll() {
        if (sizeIn + sizeOut == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (sizeOut == 0) {
            for (; sizeIn > 0; sizeIn--) {
                out.push(in.pop());
                sizeOut++;
            }
        }

        T elem = out.pop();
        sizeOut--;
        return elem;
    }

    public void push(T value) {
        in.push(value);
        sizeIn++;
    }

    public static void main(String[] args) {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        System.out.println(queue.poll());
        queue.push(3);

        System.out.println(queue.poll());
    }
}