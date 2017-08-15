package org.dan.csvql;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.dan.csvql.Exceptions.unchecked;
import static org.dan.csvql.Checks.ensure;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class CsvLoader {
    private final List<Column> columns;

    public CsvLoader(List<Column> columns) {
        this.columns = columns;
    }

    public RelationalSet load(BufferedReader reader) {
        return unchecked(() -> {
            final List<Relation> elements = new ArrayList<>();
            final int expectedColumns = columns.size();
            final Counter lineNumber = new Counter("Line", 1);
            while (true) {
                final String line = reader.readLine();
                if (line == null) {
                    break;
                }
                final List<String> rawColumnValues = asList(line.split(","));
                ensure(rawColumnValues.size() == expectedColumns,
                        () -> format("Line [%d] has [%d] columns but required [%d]",
                                lineNumber, rawColumnValues.size(), expectedColumns));
                elements.add(
                        parseLine(expectedColumns, lineNumber, rawColumnValues));
            }
            return new ListBasedRelationalSet(new MetaRelation(columns), elements);
        });
    }

    private Relation parseLine(int expectedColumns, Counter lineNumber,
            List<String> rawColumnValues) {
        final List<ColumnValue> relationValues = new ArrayList<>();
        for (int iCol = 0; iCol < expectedColumns; ++iCol) {
            final ScalarValueType type = columns.get(iCol).getType();
            final String rawValue = rawColumnValues.get(iCol);
            try {
                relationValues.add(type.defrost(rawValue));
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                       format("Line [%d] Column [%d] Value [%s] is not %s",
                               lineNumber, iCol + 1, rawValue, type), e);
            }
        }
        lineNumber.inc();
        return new RelationArray(relationValues);
    }
}
