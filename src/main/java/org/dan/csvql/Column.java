package org.dan.csvql;

public class Column {
    private ColumnName name;
    private ScalarValueType type;

    public Column(ColumnName name, ScalarValueType type) {
        this.name = name;
        this.type = type;
    }

    public ColumnName getName() {
        return name;
    }

    public void setName(ColumnName name) {
        this.name = name;
    }

    public ScalarValueType getType() {
        return type;
    }

    public void setType(ScalarValueType type) {
        this.type = type;
    }

    public static Column ofString(String name) {
        return of(name, ScalarValueType.STRING);
    }

    public static Column ofInt(String name) {
        return of(name, ScalarValueType.INT);
    }

    public static Column ofDouble(String name) {
        return of(name, ScalarValueType.DOUBLE);
    }

    private static Column of(String name, ScalarValueType type) {
        return new Column(new ColumnName(name), type);
    }
}
