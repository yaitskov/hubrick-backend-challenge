package org.dan.csvql;

public interface ColumnValue extends Comparable<ColumnValue> {
    String freeze();
    int compareTo(ColumnValue value);
}
