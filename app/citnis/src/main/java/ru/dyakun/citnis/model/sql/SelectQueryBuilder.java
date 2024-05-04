package ru.dyakun.citnis.model.sql;

import ru.dyakun.citnis.model.selection.SortType;

public class SelectQueryBuilder {

    private final StringBuilder builder = new StringBuilder();
    private int conditionsCount = 0;
    private boolean isFirstCond = true;

    public SelectQueryBuilder select(String columns) {
        builder.append(String.format("SELECT %s\n", columns));
        return this;
    }

    public SelectQueryBuilder from(String relation) {
        builder.append(String.format("\tFROM %s\n", relation));
        return this;
    }

    public SelectQueryBuilder join(String relation) {
        builder.append(String.format("\tJOIN %s\n", relation));
        return this;
    }

    public SelectQueryBuilder where(int conditionsCount) {
        if(conditionsCount < 0) {
            throw new IllegalArgumentException("Conditions count must be >= 0");
        }
        this.conditionsCount = conditionsCount;
        if(conditionsCount > 0) {
            builder.append("\tWHERE ");
        }
        return this;
    }

    public SelectQueryBuilder and(boolean needToInsert, String format, Object... args) {
        if(needToInsert) {
            conditionsCount--;
            if(isFirstCond) {
                isFirstCond = false;
            } else {
                builder.append("\t\t");
            }
            builder.append(String.format(format, args));
            if(conditionsCount != 0) {
                builder.append(" AND");
            }
            builder.append('\n');
        }
        return this;
    }

    public SelectQueryBuilder orderBy(String field, SortType type) {
        builder.append(String.format("\tORDER BY %s %s;\n", field, type.getSqlSortType()));
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
