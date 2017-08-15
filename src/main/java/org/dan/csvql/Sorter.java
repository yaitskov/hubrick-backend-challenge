package org.dan.csvql;

import static java.util.Arrays.asList;
import static org.dan.csvql.Projector.findIndexes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    private final List<Column> columns;

    public Sorter(Column... columns) {
        this.columns = asList(columns);
    }

    public RelationalSet sort(RelationalSet origin) {
        List<Relation> elements = new ArrayList<>(origin.size());

        for (Relation relation : origin) {
            elements.add(relation);
        }
        elements.sort(new RelationalComparator(
                findIndexes(columns, origin.getMetaRelation())));
        return new ListBasedRelationalSet(origin.getMetaRelation(), elements);
    }

    private static class RelationalComparator implements Comparator<Relation> {
        private final List<Integer> orderIndexes;

        private RelationalComparator(List<Integer> orderIndexes) {
            this.orderIndexes = orderIndexes;
        }

        @Override
        public int compare(Relation r1, Relation r2) {
            for (int idx : orderIndexes) {
                final int result = r1.get(idx).compareTo(r2.get(idx));
                if (result == 0) {
                    continue;
                }
                return result;
            }
            return 0;
        }
    }
}
