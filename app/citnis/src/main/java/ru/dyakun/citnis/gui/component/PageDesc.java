package ru.dyakun.citnis.gui.component;

public enum PageDesc {
    AtsSubscribers("Абоненты АТС"),
    FreeNumbers("Свободные номера");

    private final String title;

    public String getTitle() {
        return title;
    }

    PageDesc(String title) {
        this.title = title;
    }
}
