package ru.vsu.cs.gelem.task2;

public class Student {
    private String name;
    private int course;
    public Student (String name, int course) {
        this.name = name;
        this.course = course;
    }

    public int getCourse() {
        return this.course;
    }

    public String getName() {
        return this.name;
    }
}
