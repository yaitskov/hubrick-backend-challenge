package org.dan.csvql;

public class TrueRelationalPredicateFactory implements RelationalPredicateFactory {
    @Override
    public RelationalPredicate create(MetaRelation a, MetaRelation b) {
        return new TrueRelationalPredicate();
    }
}
