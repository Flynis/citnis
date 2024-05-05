package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.Form;

public interface Operation {

    Form getForm();

    String getQuery();

    String getSuccessMessage();

    String getErrorMessage(Throwable e);

    String getName();

    boolean needCallFunction();

}
