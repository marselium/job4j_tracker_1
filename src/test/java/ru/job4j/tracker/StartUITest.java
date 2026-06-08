package ru.job4j.tracker;

import org.junit.jupiter.api.Test;
import ru.job4j.action.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StartUITest {
    @Test
    void whenCreateItem() {
        Output out = new StubOutput();
        Input in = new StubInput(
                List.of(new String[]{"0", "Item name", "1"})
        );
        Tracker tracker = new Tracker();
        UserAction[] actions = {
                new Create(out),
                new Exit(out)
        };
        new StartUI(out).init(in, tracker, List.of(actions));
        assertThat(tracker.findAll().get(0).getName()).isEqualTo("Item name");
    }

    @Test
    void whenReplaceItem() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Input in = new StubInput(
                List.of(new String[]{"0", String.valueOf(item.getId()), replacedName, "1"})
        );
        UserAction[] actions = {
                new Replace(out),
                new Exit(out)
        };
        new StartUI(out).init(in, tracker, List.of(actions));
        assertThat(tracker.findById(item.getId()).getName()).isEqualTo(replacedName);
    }

    @Test
    void whenDeleteItem() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = new Item();
        Input in = new StubInput(
                List.of(new String[]{"0", String.valueOf(item.getId()), "1"})
        );
        UserAction[] actions = {
                new Delete(out),
                new Exit(out)
        };
        new StartUI(out).init(in, tracker, List.of(actions));
        assertThat(tracker.findById(item.getId())).isNull();
    }

    @Test
    void whenExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                List.of(new String[]{"0"})
        );
        Tracker tracker = new Tracker();
        UserAction[] actions = {
                new Exit(out)
        };
        new StartUI(out).init(in, tracker, List.of(actions));
        assertThat(out.toString()).hasToString(
                "Меню:" + System.lineSeparator()
                        + "0. Завершить программу" + System.lineSeparator()
                + "=== Завершение программы ===" + System.lineSeparator()
        );
    }

    @Test
    void whenReplaceItemTestOutputIsSuccessfully() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item one = tracker.add(new Item("test1"));
        String replaceName = "New Test Name";
        Input in = new StubInput(List.of(new String[]{"0", String.valueOf(one.getId()), replaceName, "1"}));
        UserAction[] actions = new UserAction[]{new Replace(out), new Exit(out)};
        new StartUI(out).init(in, tracker, List.of(actions));
        String ln = System.lineSeparator();
        assertThat(out.toString()).hasToString("Меню:" + ln
                + "0. Изменить заявку" + ln
                + "1. Завершить программу" + ln
                + "=== Редактирование заявки ===" + ln
                + "Заявка успешно изменена." + ln
                + "Меню:" + ln
                + "0. Изменить заявку" + ln
                + "1. Завершить программу" + ln
                + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenFindAllAction() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item one = tracker.add(new Item("test"));
        Input in = new StubInput(List.of(new String[]{"0", "1"}));
        UserAction[] actions = new UserAction[]{new FindAll(out), new Exit(out)};
        new StartUI(out).init(in, tracker, List.of(actions));
        String ln = System.lineSeparator();
        assertThat(out.toString()).hasToString("Меню:" + ln
                + "0. Показать все заявки" + ln
                + "1. Завершить программу" + ln
                + "=== Вывод всех заявок ===" + ln
                + one + ln
                + "Меню:" + ln
                + "0. Показать все заявки" + ln
                + "1. Завершить программу" + ln
                + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenFindByName() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item one = tracker.add(new Item("test"));
        Input in = new StubInput(List.of(new String[]{"0", one.getName(), "1"}));
        UserAction[] actions = new UserAction[]{new FindByName(out), new Exit(out)};
        new StartUI(out).init(in, tracker, List.of(actions));
        String ln = System.lineSeparator();
        assertThat(out.toString()).hasToString("Меню:" + ln
                + "0. Показать заявки по имени" + ln
                + "1. Завершить программу" + ln
                + "=== Вывод заявок по имени ===" + ln
                + one + ln
                + "Меню:" + ln
                + "0. Показать заявки по имени" + ln
                + "1. Завершить программу" + ln
                + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenFindById() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item one = tracker.add(new Item("test"));
        Input in = new StubInput(List.of(new String[]{"0", String.valueOf(one.getId()), "1"}));
        UserAction[] actions = new UserAction[]{new FindById(out), new Exit(out)};
        new StartUI(out).init(in, tracker, List.of(actions));
        String ln = System.lineSeparator();
        assertThat(out.toString()).hasToString("Меню:" + ln
                + "0. Показать заявку по id" + ln
                + "1. Завершить программу" + ln
                + "=== Вывод заявки по id ===" + ln
                + one + ln
                + "Меню:" + ln
                + "0. Показать заявку по id" + ln
                + "1. Завершить программу" + ln
                + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenInvalidExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                List.of(new String[]{"9", "0"})
        );
        Tracker tracker = new Tracker();
        UserAction[] actions = new UserAction[]{
                new Exit(out)
        };
        new StartUI(out).init(in, tracker, List.of(actions));
        String ln = System.lineSeparator();
        assertThat(out.toString()).hasToString(
                "Меню:" + ln
                        + "0. Завершить программу" + ln
                        + "Неверный ввод, вы можете выбрать: 0 .. 0" + ln
                        + "Меню:" + ln
                        + "0. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln
        );
    }
}