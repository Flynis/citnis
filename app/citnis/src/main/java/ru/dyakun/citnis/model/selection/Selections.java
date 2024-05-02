package ru.dyakun.citnis.model.selection;

public class Selections {

    private Selections() {
        throw new AssertionError();
    }

    public static boolean isChosen(String selection) {
        return !selection.equals(SelectionStorage.notChosen);
    }

    public static boolean isAnyDuration(String duration) {
        return Duration.fromLabel(duration) == Duration.ANY;
    }

}
