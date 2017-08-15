package org.dan.csvql;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

public class StringValue implements ColumnValue {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String freeze() {
        return value;
    }

    @Override
    public int compareTo(ColumnValue value) {
        return CASE_INSENSITIVE_ORDER.compare(this.value, value.freeze());
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != StringValue.class) {
            return false;
        }
        return value.equals(((StringValue) o).value);
    }

    public String toString() {
        return String.format("\"%s\"", value);
    }
}
