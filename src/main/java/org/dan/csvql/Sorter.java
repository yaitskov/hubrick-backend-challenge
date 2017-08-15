package org.dan.csvql;

import static java.util.Arrays.asList;
import static org.dan.csvql.Projector.findIndexes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Sorter {
    private final List<Column> columns;

    public Sorter(Column... columns) {
        this.columns = asList(columns);
    }

    public RelationalSet sort(RelationalSet origin) {
        return new ListBasedRelationalSet(origin.getMetaRelation(),
                new ArrayList<>(new TreeSet<>(
                        new RelationalComparator(
                                findIndexes(columns, origin.getMetaRelation())))));
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
