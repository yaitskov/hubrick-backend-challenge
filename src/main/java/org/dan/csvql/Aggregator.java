package org.dan.csvql;

import java.util.List;
import java.util.function.Function;

public interface Aggregator extends Function<List<ColumnValue>, ColumnValue> {}
