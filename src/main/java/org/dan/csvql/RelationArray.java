package org.dan.csvql;

import static org.dan.csvql.Arrays.concat;

import java.util.List;

public class RelationArray implements Relation {
    private final List<ColumnValue> columnValues;

    public RelationArray(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }

    public int size() {
        return columnValues.size();
    }

    public ColumnValue get(int idx) {
        return columnValues.get(idx);
    }

    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    @Override
    public Relation join(Relation r) {
        return new RelationArray(concat(columnValues, r.getColumnValues()));
    }

    public String toString() {
        return "[" + columnValues.stream().map(Object::toString)
                .reduce((a, b) -> String.format("%s, %s", a, b))
                .orElse("")
                + "]";
    }
}
