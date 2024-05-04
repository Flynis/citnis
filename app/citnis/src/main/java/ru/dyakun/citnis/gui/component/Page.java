package ru.dyakun.citnis.gui.component;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public abstract class Page {

    private static int nextId = 0;

    protected final int id;
    protected final String title;
    protected final BorderPane pane;

    public Page(String title) {
        this.title = title;
        id = nextId;
        nextId++;

        pane = new BorderPane();
        pane.setPrefHeight(Double.POSITIVE_INFINITY);
        pane.setPrefWidth(Double.POSITIVE_INFINITY);

        Label pageTitle = new Label(title);
        pageTitle.getStyleClass().add("page-title");

        BorderPane pageHeader = new BorderPane();
        pageHeader.setPrefHeight(40.0);
        pageHeader.setPrefWidth(Double.POSITIVE_INFINITY);
        pageHeader.getStyleClass().add("page-header");
        pageHeader.setPadding(new Insets(5, 0, 0, 30));
        pageHeader.setLeft(pageTitle);

        pane.setTop(pageHeader);
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void reset() {}

    public Node getNode() {
        return pane;
    }

}
