package ru.job4j.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        if (!linked.iterator().hasNext()) {
            throw new NoSuchElementException();
        }
        T poped = linked.get(0);
        linked.deleteFirst();
        return poped;
    }

    public void push(T value) {
        ForwardLinked<T> newLinked = new ForwardLinked<>();
        newLinked.add(value);
        Iterator<T> iterator = linked.iterator();
        while (iterator.hasNext()) {
            newLinked.add(iterator.next());
        }
        linked = newLinked;
    }

    public static void main(String[] args) {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.pop();
        System.out.println(stack.pop());
    }
}