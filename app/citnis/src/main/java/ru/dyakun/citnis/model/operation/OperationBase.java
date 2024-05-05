package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.Form;
import ru.dyakun.citnis.model.db.UpdateListener;
import ru.dyakun.citnis.model.selection.SelectionStorage;

public abstract class OperationBase implements Operation, UpdateListener {

    protected Form form;

    public OperationBase() {
        SelectionStorage.getInstance().addUpdateListener(this);
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public boolean needCallFunction() {
        return true;
    }

}
