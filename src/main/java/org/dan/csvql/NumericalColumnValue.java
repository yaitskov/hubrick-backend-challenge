package org.dan.csvql;

public interface NumericalColumnValue {
    <T extends Number> T getValue();
    NumericalColumnValue sum(NumericalColumnValue b);
}
