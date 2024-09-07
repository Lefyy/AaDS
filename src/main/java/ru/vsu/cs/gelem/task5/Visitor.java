package ru.vsu.cs.gelem.task5;

public interface Visitor<T> {
    void accept(T value);
}
