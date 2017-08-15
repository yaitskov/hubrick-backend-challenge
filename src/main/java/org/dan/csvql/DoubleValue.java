package org.dan.csvql;

import static java.lang.String.valueOf;

public class DoubleValue implements ColumnValue, NumericalColumnValue {
    private final double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String freeze() {
        return valueOf(value);
    }

    @Override
    public int compareTo(ColumnValue value) {
        return Double.compare(this.value, ((DoubleValue) value).value);
    }

    @Override
    public NumericalColumnValue sum(NumericalColumnValue b) {
        return new DoubleValue(value + ((DoubleValue) b).value);
    }
}
