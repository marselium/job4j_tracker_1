package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateInputTest {

    @Test
    void whenInvalidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                List.of(new String[]{"one", "1"})
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected).isEqualTo(1);
    }

    @Test
    void whenValidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(List.of(new String[]{"1"}));
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu: ");
        assertThat(selected).isEqualTo(1);
    }

    @Test
    void whenManyValidInputs() {
        Output out = new StubOutput();
        Input in = new StubInput(List.of(new String[]{"1", "7"}));
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu: ");
        assertThat(selected).isEqualTo(1);
        selected = input.askInt("Enter menu: ");
        assertThat(selected).isEqualTo(7);

    }

    @Test
    void whenNegativeInput() {
        Output out = new StubOutput();
        Input in = new StubInput(List.of(new String[]{"-1"}));
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu: ");
        assertThat(selected).isEqualTo(-1);
    }
}