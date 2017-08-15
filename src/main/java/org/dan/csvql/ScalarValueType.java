package org.dan.csvql;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public enum ScalarValueType {
    STRING {
        @Override
        public ColumnValue defrost(String value) {
            return new StringValue(value);
        }
    },
    INT {
        @Override
        public ColumnValue defrost(String value) {
            return new IntValue(parseInt(value));
        }
    },
    DOUBLE {
        @Override
        public ColumnValue defrost(String value) {
            return new DoubleValue(parseDouble(value));
        }
    };

    public abstract ColumnValue defrost(String value);
}
