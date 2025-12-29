package feup2526.ldts.t02g03.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LanternaGUIFactoryTest {
    private LanternaGUIFactory factory;
    private GUI gui;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, FontFormatException {
        gui = Mockito.mock(GUI.class);

        factory = new LanternaGUIFactory();
    }

    @Test
    void getGridWidth() {
        // TILE_SIZE is 16 defined in LanternaGUIFactory
        // Width 320 -> 20 tiles
        when(gui.getTerminalWidth()).thenReturn(320);
        assertEquals(20, factory.getGridWidth(gui));

        // Width 330 -> 20 tiles (integer division)
        when(gui.getTerminalWidth()).thenReturn(330);
        assertEquals(20, factory.getGridWidth(gui));
    }

    @Test
    void getGridHeight() {
        // TILE_SIZE is 16 defined in LanternaGUIFactory
        // Height 320 -> 20 tiles
        when(gui.getTerminalHeight()).thenReturn(320);
        assertEquals(20, factory.getGridHeight(gui));

        // Height 160 -> 10 tiles
        when(gui.getTerminalHeight()).thenReturn(160);
        assertEquals(10, factory.getGridHeight(gui));
    }
}
