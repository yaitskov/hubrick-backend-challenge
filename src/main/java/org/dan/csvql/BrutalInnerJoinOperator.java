package org.dan.csvql;

import java.util.ArrayList;
import java.util.List;

public class BrutalInnerJoinOperator {
    private final RelationalPredicateFactory predicateFactory;

    public BrutalInnerJoinOperator(RelationalPredicateFactory predicateFactory) {
        this.predicateFactory = predicateFactory;
    }

    public RelationalSet apply(RelationalSet a, RelationalSet b) {
        final RelationalPredicate predicate = predicateFactory
                .create(a.getMetaRelation(), b.getMetaRelation());
        final List<Relation> joinedRelations = new ArrayList<>();
        for (Relation rA : a) {
            for (Relation rB : b) {
                if (predicate.test(rA, rB)) {
                    joinedRelations.add(rA.join(rB));
                }
            }
        }
        return new ListBasedRelationalSet(
                a.getMetaRelation().join(b.getMetaRelation()),
                joinedRelations);
    }
}
