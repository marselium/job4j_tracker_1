package ru.job4j.action;

import ru.job4j.tracker.Input;
import ru.job4j.tracker.MemTracker;

public interface UserAction {
    String name();

    boolean execute(Input input, MemTracker memTracker);
}
