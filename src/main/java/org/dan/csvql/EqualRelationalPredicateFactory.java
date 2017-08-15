package org.dan.csvql;

public class EqualRelationalPredicateFactory implements RelationalPredicateFactory {
    private final ColumnName columnNameA;
    private final ColumnName columnNameB;

    public EqualRelationalPredicateFactory(ColumnName columnName) {
        this(columnName, columnName);
    }

    public EqualRelationalPredicateFactory(ColumnName columnNameA, ColumnName columnNameB) {
        this.columnNameA = columnNameA;
        this.columnNameB = columnNameB;
    }

    public EqualRelationalPredicate create(MetaRelation a, MetaRelation b) {
        return new EqualRelationalPredicate(
                a.columnIndex(columnNameA),
                b.columnIndex(columnNameB));
    }
}
