package org.dan.csvql;

import java.util.ArrayList;
import java.util.List;

public class MetaRelation {
    private final List<Column> columns;

    public MetaRelation(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public MetaRelation join(MetaRelation extension) {
        final List<Column> joinedColumns = new ArrayList<>();
        joinedColumns.addAll(columns);
        joinedColumns.addAll(extension.columns);
        return new MetaRelation(joinedColumns);
    }

    public int columnIndex(ColumnName columnName) {
        for (int idx = 0; idx < columns.size(); ++idx) {
            if (columns.get(idx).getName().equals(columnName)) {
                return idx;
            }
        }
        throw new IllegalArgumentException("No column [" + columnName + "]");
    }
}
