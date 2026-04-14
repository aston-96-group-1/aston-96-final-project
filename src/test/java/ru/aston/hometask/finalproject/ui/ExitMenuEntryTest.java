package ru.aston.hometask.finalproject.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.constants.Strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExitMenuEntryTest {
    private ExitMenuEntry exitMenuEntry;

    @BeforeEach
    void setup() {
        exitMenuEntry = new ExitMenuEntry();
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertEquals(Strings.EXIT_MENU_TITLE.get(), exitMenuEntry.getDescription());
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        String state = exitMenuEntry.getState();

        assertEquals("", state);
    }
}
