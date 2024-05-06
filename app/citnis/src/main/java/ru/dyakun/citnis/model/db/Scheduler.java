package ru.dyakun.citnis.model.db;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    private static final Scheduler instance = new Scheduler();

    private final Timer timer = new Timer();

    private Scheduler() {}

    public static Scheduler getInstance() {
        return instance;
    }

    public void schedule(TimerTask task, long period) {
        timer.schedule(task, period, period);
    }

    public void stop() {
        timer.cancel();
    }

}
