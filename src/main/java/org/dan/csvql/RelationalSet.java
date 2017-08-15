package org.dan.csvql;

import java.util.Iterator;

public interface RelationalSet extends Iterable<Relation> {
    MetaRelation getMetaRelation();
    Iterator<Relation> iterator();
    int size();
}
