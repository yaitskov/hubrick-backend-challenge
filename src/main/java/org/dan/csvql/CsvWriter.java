package org.dan.csvql;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class CsvWriter {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String LINE_SEPARATOR = "\n";

    private final Consumer<String> out;

    public CsvWriter(Consumer<String> out) {
        this.out = out;
    }

    private void write(Relation element) {
        write(element.getColumnValues(), ColumnValue::freeze);
    }

    public void write(RelationalSet set) {
        write(set.getMetaRelation()
                .getColumns(), column -> column.getName().getName());
        for (Relation relation : set) {
            write(relation);
        }
    }

    private <T> void write(Iterable<T> set, Function<T, String> freezer) {
        final Iterator<T> columnIterator = set.iterator();
        while (columnIterator.hasNext()) {
            final T value = columnIterator.next();
            out.accept(freezer.apply(value));
            if (columnIterator.hasNext()){
                out.accept(COLUMN_SEPARATOR);
            }
        }
        out.accept(LINE_SEPARATOR);
    }
}
