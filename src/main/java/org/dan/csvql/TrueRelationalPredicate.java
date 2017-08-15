package org.dan.csvql;

public class TrueRelationalPredicate implements RelationalPredicate {
    @Override
    public boolean test(Relation a, Relation b) {
        return true;
    }
}
