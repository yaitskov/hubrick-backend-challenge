package org.dan.csvql;

import java.util.ArrayList;
import java.util.List;

public interface Arrays {
    static <T> List<T> concat(List<T> a, List<T> b){
        List<T> result = new ArrayList<T>(a.size() + b.size());
        result.addAll(a);
        result.addAll(b);
        return result;
    }
}
