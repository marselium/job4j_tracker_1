package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemTrackerTest {

    @Test
    void whenAddNewItemThenTrackerHasSameItem() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item();
        item.setName("test1");
        memTracker.add(item);
        Item result = memTracker.findById(item.getId());
        assertThat(result.getName()).isEqualTo(item.getName());
    }

    @Test
    void whenTestFindById() {
        MemTracker memTracker = new MemTracker();
        Item bug = new Item("Bug");
        Item item = memTracker.add(bug);
        Item result = memTracker.findById(item.getId());
        assertThat(result.getName()).isEqualTo(item.getName());
    }

    @Test
    void whenTestFindAll() {
        MemTracker memTracker = new MemTracker();
        Item first = new Item("First");
        Item second = new Item("Second");
        memTracker.add(first);
        memTracker.add(second);
        Item result = memTracker.findAll().get(0);
        assertThat(result.getName()).isEqualTo(first.getName());
    }

    @Test
    void whenTestFindByNameCheckArrayLength() {
        MemTracker memTracker = new MemTracker();
        Item first = new Item("First");
        Item second = new Item("Second");
        memTracker.add(first);
        memTracker.add(second);
        memTracker.add(new Item("First"));
        memTracker.add(new Item("Second"));
        memTracker.add(new Item("First"));
        List<Item> result = memTracker.findByName(first.getName());
        assertThat(result).hasSize(3);
    }

    @Test
    void whenReplaceItemIsSuccessful() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        int id = item.getId();
        Item updateItem = new Item("Bug with description");
        memTracker.replace(id, updateItem);
        assertThat(memTracker.findById(id).getName()).isEqualTo("Bug with description");
    }

    @Test
    void whenReplaceItemIsNotSuccessful() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        Item updateItem = new Item("Bug with description");
        boolean result = memTracker.replace(1000, updateItem);
        assertThat(memTracker.findById(item.getId()).getName()).isEqualTo("Bug");
        assertThat(result).isFalse();
    }

    @Test
    void whenDeleteItemIsSuccessful() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        int id = item.getId();
        memTracker.delete(id);
        assertThat(memTracker.findById(id)).isNull();
    }

    @Test
    void whenDeleteItemIsNotSuccessful() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        memTracker.delete(1000);
        assertThat(memTracker.findById(item.getId()).getName()).isEqualTo("Bug");
    }
}