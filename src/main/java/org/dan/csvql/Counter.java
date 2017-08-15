package org.dan.csvql;

public class Counter {
    private final String name;
    private int value;

    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public void inc() {
        ++value;
    }

    public String toString() {
        return name + ": [" + value + "]";
    }
}
