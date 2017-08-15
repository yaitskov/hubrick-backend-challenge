package org.dan.csvql;

import java.util.List;

public interface Relation {
    int size();
    ColumnValue get(int idx);
    Relation join(Relation r);
    List<ColumnValue> getColumnValues();
}
