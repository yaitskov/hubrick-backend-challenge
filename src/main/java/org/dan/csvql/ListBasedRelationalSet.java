package org.dan.csvql;

import java.util.Iterator;
import java.util.List;

public class ListBasedRelationalSet implements RelationalSet {
    private final MetaRelation metaRelation;
    private final List<Relation> elements;

    public ListBasedRelationalSet(MetaRelation metaRelation, List<Relation> elements) {
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
