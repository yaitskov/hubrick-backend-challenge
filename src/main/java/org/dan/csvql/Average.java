package org.dan.csvql;

import java.util.List;

public class Average implements Aggregator {
    public ScalarValueType getType() {
        return ScalarValueType.DOUBLE;
    }

    @Override
    public ColumnValue apply(List<ColumnValue> columnValues) {
        if (columnValues.isEmpty()) {
            return NullValue.NULL_VALUE;
        }
        return new DoubleValue(
                columnValues.stream()
                        .map(v -> (NumericalColumnValue) v)
                        .reduce((a, b) -> a.sum(b))
                        .map(columnValue -> (double) columnValue.getValue() / columnValues.size())
                        .get());
    }
}
