package ru.dyakun.citnis.form.structure;

import com.dlsc.formsfx.model.event.FormEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Form {

    protected final List<Field<?>> fields = new ArrayList<>();

    protected final BooleanProperty valid = new SimpleBooleanProperty(true);

    private final Map<EventType<FormEvent>,List<EventHandler<? super FormEvent>>> eventHandlers = new ConcurrentHashMap<>();


    private Form(Field<?>... fields) {
        Collections.addAll(this.fields, fields);

        this.fields.forEach(s -> s.validProperty().addListener((observable, oldValue, newValue) -> setValidProperty()));

        setValidProperty();
    }

    public static Form of(Field<?>... fields) {
        return new Form(fields);
    }

    protected void setValidProperty() {
        valid.setValue(fields.stream().allMatch(Field::isValid));
    }


    public List<Field<?>> getFields() {
        return fields;
    }

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public Form addEventHandler(EventType<FormEvent> eventType, EventHandler<? super FormEvent> eventHandler) {
        if (eventType == null) {
            throw new NullPointerException("Argument eventType must not be null");
        }
        if (eventHandler == null) {
            throw new NullPointerException("Argument eventHandler must not be null");
        }

        this.eventHandlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(eventHandler);

        return this;
    }

    public Form removeEventHandler(EventType<FormEvent> eventType, EventHandler<? super FormEvent> eventHandler) {
        if (eventType == null) {
            throw new NullPointerException("Argument eventType must not be null");
        }
        if (eventHandler == null) {
            throw new NullPointerException("Argument eventHandler must not be null");
        }

        List<EventHandler<? super FormEvent>> list = this.eventHandlers.get(eventType);
        if (list != null) {
            list.remove(eventHandler);
        }

        return this;
    }

    protected void fireEvent(FormEvent event) {
        List<EventHandler<? super FormEvent>> list = this.eventHandlers.get(event.getEventType());
        if (list == null) {
            return;
        }
        for (EventHandler<? super FormEvent> eventHandler : list) {
            if (!event.isConsumed()) {
                eventHandler.handle(event);
            }
        }
    }

}
