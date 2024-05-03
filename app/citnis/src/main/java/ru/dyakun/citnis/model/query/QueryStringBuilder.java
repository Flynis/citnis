package ru.dyakun.citnis.model.query;

public class QueryStringBuilder {

    private final StringBuilder builder = new StringBuilder();
    private int conditionsCount = 0;
    private boolean isFirstCond = true;

    public QueryStringBuilder select(String columns) {
        builder.append(String.format("SELECT %s\n", columns));
        return this;
    }

    public QueryStringBuilder from(String relation) {
        builder.append(String.format("\tFROM %s\n", relation));
        return this;
    }

    public QueryStringBuilder join(String relation) {
        builder.append(String.format("\tJOIN %s\n", relation));
        return this;
    }

    public QueryStringBuilder where(int conditionsCount) {
        if(conditionsCount < 0) {
            throw new IllegalArgumentException("Conditions count must be >= 0");
        }
        this.conditionsCount = conditionsCount;
        if(conditionsCount > 0) {
            builder.append("\tWHERE ");
        }
        return this;
    }

    public QueryStringBuilder and(boolean needToInsert, String format, Object... args) {
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

    public QueryStringBuilder orderBy(String field, String sortType) {
        builder.append(String.format("\tORDER BY %s %s;\n", field, sortType));
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
