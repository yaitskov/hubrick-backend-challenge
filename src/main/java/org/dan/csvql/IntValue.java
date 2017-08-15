package org.dan.csvql;

import static java.lang.String.valueOf;

public class IntValue implements ColumnValue, NumericalColumnValue {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String freeze() {
        return valueOf(value);
    }

    @Override
    public int compareTo(ColumnValue value) {
        return Integer.compare(this.value, ((IntValue) value).value);
    }

    @Override
    public NumericalColumnValue sum(NumericalColumnValue b) {
        return new DoubleValue(value + ((IntValue) b).value);
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != IntValue.class) {
            return false;
        }
        return value == ((IntValue) o).value;
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
