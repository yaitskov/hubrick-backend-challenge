package org.dan.csvql;

public class NullValue implements ColumnValue {
    public static final NullValue NULL_VALUE = new NullValue();

    @Override
    public String freeze() {
        return "null";
    }

    @Override
    public int compareTo(ColumnValue value) {
        if (value instanceof NullValue) {
            return 0;
        }
        return 1;
    }

    public String toString() {
        return "null";
    }
}
