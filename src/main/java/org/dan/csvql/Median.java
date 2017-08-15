package org.dan.csvql;

import static org.dan.csvql.NullValue.NULL_VALUE;

import java.util.Collections;
import java.util.List;

public class Median implements Aggregator {
    private final double ration;

    public Median(double ration) {
        this.ration = ration;
    }

    @Override
    public ColumnValue apply(List<ColumnValue> columnValues) {
        if (columnValues.isEmpty()) {
            return NULL_VALUE;
        }
        Collections.sort(columnValues);
        return columnValues.get((int) (columnValues.size() * ration));
    }
}
