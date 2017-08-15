package org.dan.csvql;

import java.util.Collection;
import java.util.Iterator;

public class ListBasedRelationalSet implements RelationalSet {
    private final MetaRelation metaRelation;
    private final Collection<Relation> elements;

    public ListBasedRelationalSet(MetaRelation metaRelation, Collection<Relation> elements) {
        this.metaRelation = metaRelation;
        this.elements = elements;
    }

    public MetaRelation getMetaRelation() {
        return metaRelation;
    }

    public Iterator<Relation> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }
}
