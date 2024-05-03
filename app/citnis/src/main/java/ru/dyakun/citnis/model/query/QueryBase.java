package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Form;
import ru.dyakun.citnis.model.db.UpdateListener;
import ru.dyakun.citnis.model.selection.SelectionStorage;

public abstract class QueryBase<T> implements Query<T>, UpdateListener {

    protected Form form;
    protected Mapper<T> mapper;

    public QueryBase() {
        SelectionStorage.getInstance().addUpdateListener(this);
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public Mapper<T> getMapper() {
        return mapper;
    }

}
