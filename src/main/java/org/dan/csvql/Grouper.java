package org.dan.csvql;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class Grouper {
    private final ColumnName groupBy;
    private final ColumnName aggregate;
    private final Aggregator aggregator;

    public Grouper(ColumnName groupBy, ColumnName aggregate,
            Aggregator aggregator) {
        this.groupBy = groupBy;
        this.aggregate = aggregate;
        this.aggregator = aggregator;
    }

    public RelationalSet group(RelationalSet origin) {
        final int groupByIdx = origin.getMetaRelation()
                .columnIndex(groupBy);
        final int aggregateIdx = origin.getMetaRelation()
                .columnIndex(aggregate);
        final List<Relation> result = new ArrayList<>();
        final List<ColumnValue> reductionSet = new ArrayList<>();
        ColumnValue previous = null;
        for (Relation relation : origin) {
            final ColumnValue current = relation.get(groupByIdx);
            if (previous == null) {
                previous = current;
                reductionSet.add(current);
            } else if (previous.compareTo(current) == 0) {
                reductionSet.add(current);
            } else {
                result.add(new RelationArray(
                        asList(current, aggregator.apply(reductionSet))));
                reductionSet.clear();
            }
        }
        if (previous != null) {
            result.add(new RelationArray(
                    asList(previous, aggregator.apply(reductionSet))));
        }
        return new ListBasedRelationalSet(
                new MetaRelation(asList(
                        origin.getMetaRelation().getColumns().get(groupByIdx),
                        origin.getMetaRelation().getColumns().get(aggregateIdx))),
                result);
    }
}
