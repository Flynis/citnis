package ru.dyakun.citnis.gui.page;

import javafx.scene.Node;

public abstract class PageBuilder {

    private static int nextId = 0;

    protected final int id;
    protected String title = "";

    public PageBuilder() {
        id = nextId;
        nextId++;
    }

    public PageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public abstract Node build();

}
