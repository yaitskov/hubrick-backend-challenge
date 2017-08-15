package org.dan.csvql;

public interface RelationalPredicate {
    boolean test(Relation a, Relation b);
}
