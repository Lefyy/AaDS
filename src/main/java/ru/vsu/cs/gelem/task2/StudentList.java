package ru.vsu.cs.gelem.task2;

import java.util.Iterator;

public class StudentList implements Iterable<Student> {

    private class StudentItem {
        public Student value;
        public StudentItem next;

        public StudentItem(Student value, StudentItem next) {
            this.value = value;
            this.next = next;
        }

        public StudentItem(Student value) {
            this(value, null);
        }

        public StudentItem() {
            this(null, null);
        }
    }


    private StudentItem head = null;
    private StudentItem tail = null;
    private int count = 0;


    private void checkEmpty() throws Exception {
        if (isEmpty()) {
            throw new Exception("List is empty");
        }
    }

    public void sort() {
        StudentItem[] start = new StudentItem[6];
        StudentItem[] end = new StudentItem[6];

        for (StudentItem curr = head; curr != null; curr = curr.next) {
            int courseInd = curr.value.getCourse() - 1;
            if (start[courseInd] == null) {
                start[courseInd] = curr;
                end[courseInd] = curr;
            } else {
                end[courseInd].next = curr;
            }
            end[courseInd] = curr;
        }

        StudentItem newHead = null;
        StudentItem newTail = null;

        for (int i = 0; i < start.length; i++) {
            if (newHead == null) {
                newHead = start[i];
            }
            if (end[i] != null) {
                newTail = end[i];
            }
            if (newTail != null && i < 5) {
                newTail.next = start[i + 1];
            }
        }

        head = newHead;
        tail = newTail;
    }

    public Student getFirst() throws Exception {
        checkEmpty();
        return head.value;
    }

    public Student getLast() throws Exception {
        checkEmpty();
        return tail.value;
    }

    private StudentItem getItem(int index) throws Exception {
        if (index < 0 || index >= count) {
            throw new Exception("Incorrect index");
        }
        StudentItem curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    public Student get(int index) throws Exception {
        return getItem(index).value;
    }

    public void addFirst(Student value) {
        head = new StudentItem(value, head);
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    public void addLast(Student value) {
        if (tail == null) {
            head = tail = new StudentItem(value);
        } else {
            tail = tail.next = new StudentItem(value);
        }
        count++;
    }

    public void insert(int index, Student value) throws Exception {
        if (index < 0 || index > count) {
            throw new Exception("Incorrect index");
        }
        if (index == 0) {
            addFirst(value);
        } else {
            StudentItem prev = getItem(index - 1);
            prev.next = new StudentItem(value, prev.next);
            if (prev.next.next == null) {
                tail = prev.next;
            }
            count++;
        }
    }

    public Student removeFirst() throws Exception {
        checkEmpty();
        Student value = getFirst();
        head = head.next;
        if (head == null) {
            tail = null;
        }
        count--;
        return value;
    }

    public Student removeLast() throws Exception {
        return remove(count - 1);
    }

    public Student remove(int index) throws Exception {
        if (index == 0) {
            return removeFirst();
        } else {
            StudentItem prev = getItem(index - 1);
            Student value = prev.next.value;
            prev.next = prev.next.next;
            if (prev.next == null) {
                tail = prev;
            }
            count--;
            return value;
        }
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
    public Iterator<Student> iterator() {

        return new Iterator<>() {
            StudentItem curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Student next() {
                Student value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }
}
