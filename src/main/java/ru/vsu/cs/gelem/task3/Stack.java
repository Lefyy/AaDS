package ru.vsu.cs.gelem.task3;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class Stack<T> implements Iterable<T> {

    private class StackItem<T> {
        public T value;
        public StackItem<T> next;

        public StackItem(T value, StackItem<T> next) {
            this.value = value;
            this.next = next;
        }

        public StackItem(T value) {
            this(value , null);
        }

        public StackItem() {
            this(null, null);
        }
    }


    private StackItem<T> head = null;
    private StackItem<T> tail = null;
    private int count = 0;


    private void checkEmpty() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }

    public void push(T value) {
        head = new StackItem<>(value, head);
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    public T peek() throws EmptyStackException {
        checkEmpty();
        return head.value;
    }

    public T pop() throws EmptyStackException {
        checkEmpty();
        StackItem<T> elem = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        count--;
        return elem.value;
    }

    public void clear() {
        head = tail = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public Iterator<T> iterator() {
//        class MyLinkedListIterator implements Iterator<T> {
//            MyLinkedListItem<T> curr = head;
//
//            @Override
//            public boolean hasNext() {
//                return curr != null;
//            }
//
//            @Override
//            public T next() {
//                T value = curr.value;
//                curr = curr.next;
//                return value;
//            }
//        }
//
//        return new SimpleLinkedListIterator();

        return new Iterator<T>() {
            StackItem<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }
}
