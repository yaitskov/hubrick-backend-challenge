package org.dan.csvql;

import static java.util.Collections.singletonList;
import static org.dan.csvql.ScalarValueType.INT;

import java.util.Iterator;

public class EnumeratingSet implements RelationalSet {
    private final MetaRelation metaRelation;
    private int counter;

    public EnumeratingSet(ColumnName columnName) {
        metaRelation = new MetaRelation(singletonList(new Column(columnName, INT)));
    }

    @Override
    public MetaRelation getMetaRelation() {
        return metaRelation;
    }

    @Override
    public int size() {
        throw new IllegalStateException();
    }

    @Override
    public Iterator<Relation> iterator() {
        return new ShotIterator(new RelationArray(singletonList(new IntValue(++counter))));
    }

    private static class ShotIterator implements Iterator<Relation> {
        private Relation element;

        public ShotIterator(Relation element) {
            this.element = element;
        }

        @Override
        public boolean hasNext() {
            return element != null;
        }

        @Override
        public Relation next() {
            final Relation result = element;
            element = null;
            return result;
        }
    }
}
