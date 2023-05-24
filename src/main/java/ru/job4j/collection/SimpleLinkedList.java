package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    private int size = 0;
    private int modCount = 0;
    private Node<E> head;
    private Node<E> last;

    @Override
    public void add(E value) {
        final Node<E> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
            last = head;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> current = head;
        int counter = 0;
        while (counter < index) {
            current = current.next;
            counter++;
        }

        return current.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            private int expectedModCount = modCount;

            private Node<E> current = head;
            private Node<E> returned;

            @Override
            public boolean hasNext() {
                if (modCount > expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                returned = current;
                current = current.next;
                index++;

                return returned.item;
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println("---------");
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}