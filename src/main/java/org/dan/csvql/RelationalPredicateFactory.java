package org.dan.csvql;

public interface RelationalPredicateFactory {
    RelationalPredicate create(MetaRelation a, MetaRelation b);
}
