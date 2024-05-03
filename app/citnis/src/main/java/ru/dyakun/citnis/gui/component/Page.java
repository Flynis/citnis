package ru.dyakun.citnis.gui.component;

import javafx.scene.Node;

public abstract class Page {

    private static int nextId = 0;

    protected final int id;
    protected final String title;

    public Page(String title) {
        this.title = title;
        id = nextId;
        nextId++;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public abstract Node getNode();

}
