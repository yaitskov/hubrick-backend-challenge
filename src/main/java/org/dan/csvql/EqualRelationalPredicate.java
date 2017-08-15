package org.dan.csvql;

public class EqualRelationalPredicate implements RelationalPredicate {
    private final int indexA;
    private final int indexB;

    public EqualRelationalPredicate(int indexA, int indexB) {
        this.indexA = indexA;
        this.indexB = indexB;
    }

    public boolean test(Relation a, Relation b) {
        return a.get(indexA).equals(b.get(indexB));
    }
}
