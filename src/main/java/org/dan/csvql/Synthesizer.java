package org.dan.csvql;

import static java.util.Collections.singletonList;
import static org.dan.csvql.NullValue.NULL_VALUE;
import static org.dan.csvql.ScalarValueType.INT;

import java.util.ArrayList;
import java.util.List;

public class Synthesizer {
    private static final List<ColumnValue> NULL_VALUE_LIST = singletonList(NULL_VALUE);

    public RelationalSet synthesize(RelationalSet set,
            ColumnName origin,
            ColumnName target,
            SynthesizerFunc func) {
        final int columnIdx = set.getMetaRelation().columnIndex(origin);
        final List<Relation> elements = new ArrayList<>(set.size());
        for (Relation relation : set) {
            elements.add(relation.join(
                    new RelationArray(calculate(func, relation.get(columnIdx)))));
        }
        return new ListBasedRelationalSet(
                set.getMetaRelation().join(
                        new MetaRelation(
                                singletonList(
                                        new Column(target, INT)))),
                elements);
    }

    private List<ColumnValue> calculate(SynthesizerFunc func, ColumnValue originValue) {
        if (originValue == NULL_VALUE) {
            return NULL_VALUE_LIST;
        }
        return singletonList(func.apply(originValue));
    }
}