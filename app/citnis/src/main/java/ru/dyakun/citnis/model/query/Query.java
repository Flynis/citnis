package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Form;

public interface Query<T> {

    Form getForm();

    String getQuery();

    Mapper<T> getMapper();

}
