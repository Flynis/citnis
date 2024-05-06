package ru.dyakun.citnis.model.sql;

public class InsertQueryBuilder {

    private final StringBuilder insertStr = new StringBuilder();
    private final StringBuilder valuesStr = new StringBuilder();
    private int columnsCount = 0;

    public InsertQueryBuilder insertInto(String table) {
        insertStr.append(String.format("INSERT INTO %s (", table));
        valuesStr.append("\tVALUES (");
        return this;
    }

    public InsertQueryBuilder column(boolean needToInsert, String name, String val) {
        if(needToInsert) {
            if(columnsCount > 0) {
                insertStr.append(", ");
                valuesStr.append(", ");
            }
            insertStr.append(name);
            valuesStr.append(String.format("'%s'", val));
            columnsCount++;
        }
        return this;
    }

    public InsertQueryBuilder column(boolean needToInsert, String name, Number val) {
        if(needToInsert) {
            if(columnsCount > 0) {
                insertStr.append(", ");
                valuesStr.append(", ");
            }
            insertStr.append(name);
            valuesStr.append(val.toString());
            columnsCount++;
        }
        return this;
    }

    @Override
    public String toString() {
        insertStr.append(")\n");
        valuesStr.append(");\n");
        return insertStr.toString() + valuesStr;
    }

}
