package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Form;
import ru.dyakun.citnis.model.db.Mapper;

public interface Query<T> {

    Form getForm();

    String getQuery();

    Mapper<T> getMapper();

}
