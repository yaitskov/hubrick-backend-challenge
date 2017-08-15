package org.dan.csvql;

import static java.lang.String.format;
import static org.dan.csvql.Checks.ensure;

import java.util.ArrayList;
import java.util.List;

public class Projector {
    private final MetaRelation keepIt;

    public Projector(MetaRelation keepIt) {
        this.keepIt = keepIt;
    }

    public RelationalSet project(RelationalSet set) {
        final List<Integer> originIndexes = findIndexes(
                keepIt.getColumns(), set.getMetaRelation());
        final List<Relation> elements = new ArrayList<>(set.size());
        for (Relation relation : set) {
            List<ColumnValue> values = new ArrayList<>(keepIt.getColumns().size());
            for (Integer originIndex : originIndexes) {
                values.add(relation.get(originIndex));
            }
            elements.add(new RelationArray(values));
        }
        return new ListBasedRelationalSet(keepIt, elements);
    }

    public static List<Integer> findIndexes(List<Column> columns, MetaRelation origin) {
        final List<Integer> result = new ArrayList<>(columns.size());
        next:
        for (Column column : columns) {
            for (int idx = 0; idx < origin.getColumns().size(); ++idx) {
                final Column originColumn = origin.getColumns().get(idx);
                if (originColumn.getName().equals(column.getName())) {
                    ensure(originColumn.getType() == column.getType(),
                            () -> format("Column type mismatch %s <> %s" ,
                                    originColumn.getType(), column.getType()));
                    result.add(idx);
                    continue next;
                }
            }
            throw new IllegalStateException("Origin relation ["
                    + origin + "] does not have column ["
                    + column.getName() + "]");
        }
        return result;
    }
}
