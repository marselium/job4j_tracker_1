package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {
    @Test
    void whenSortDown() {
        List<Item> items = Arrays.asList(
                new Item("see"),
                new Item("goodbye"),
                new Item("hello")
        );
        List<Item> exp = Arrays.asList(
                new Item("see"),
                new Item("hello"),
                new Item("goodbye")
        );
        items.sort(new ItemDescByName());
        assertThat(items).isEqualTo(exp);
    }

    @Test
    void whenSortUp() {
        List<Item> items = Arrays.asList(
                new Item("see"),
                new Item("goodbye"),
                new Item("hello")
        );
        List<Item> exp = Arrays.asList(
                new Item("goodbye"),
                new Item("hello"),
                new Item("see")
        );
        items.sort(new ItemAscByName());
        assertThat(items).isEqualTo(exp);
    }
}
