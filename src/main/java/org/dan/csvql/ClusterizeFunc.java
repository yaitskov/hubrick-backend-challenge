package org.dan.csvql;

public class ClusterizeFunc implements SynthesizerFunc {
    private final int range;

    public ClusterizeFunc(int range) {
        this.range = range;
    }

    @Override
    public ColumnValue apply(ColumnValue columnValue) {
        final int value = ((IntValue) columnValue).getValue();
        return new IntValue(value - (value % 10));
    }
}
