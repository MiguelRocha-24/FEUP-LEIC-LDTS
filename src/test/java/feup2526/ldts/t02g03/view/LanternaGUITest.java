package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanternaGUITest {
    private Screen screen;
    private LanternaGUI gui;
    private TextGraphics tg;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        tg = Mockito.mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(tg);
        gui = new LanternaGUI(screen);
    }

    @Test
    void testCreateOffScreenImage() {
        GUIImage image = gui.createOffScreenImage(10, 5);
        assertNotNull(image);
        assertEquals(10, image.getWidth());
        assertEquals(5, image.getHeight());
        assertInstanceOf(LanternaGUIImage.class, image);
    }

    @Test
    void testDrawText() {
        gui.drawText(1, 1, "Hello", "#FFFFFF");
        verify(tg, times(1)).putString(1, 1, "Hello");
    }

    @Test
    void testScreenOperations() throws IOException {
        gui.clear();
        verify(screen).clear();

        gui.refresh();
        verify(screen).refresh();

        gui.close();
        verify(screen).close();
    }
    
    @Test
    void testDrawImageOpaque() {
        LanternaGUIImage image = new LanternaGUIImage(5, 5);
        image.setTransparency(false);
        
        gui.drawImage(1, 1, image);
        
        verify(tg).drawImage(eq(new com.googlecode.lanterna.TerminalPosition(1, 1)), any());
    }

    @Test
    void testDrawImageTransparent() {
        LanternaGUIImage image = new LanternaGUIImage(2, 2);
        image.setTransparency(true);
        image.setPixel(0, 0, "#FFFFFF");
        
        gui.drawImage(1, 1, image);

        verify(tg, atLeastOnce()).setCharacter(anyInt(), anyInt(), any());
    }

    @Test
    void testReadInput() throws IOException {
        com.googlecode.lanterna.input.KeyStroke keyStroke = new com.googlecode.lanterna.input.KeyStroke(com.googlecode.lanterna.input.KeyType.Enter);
        when(screen.pollInput()).thenReturn(keyStroke);
        
        assertEquals(keyStroke, gui.readInput());
    }

    @Test
    void testCreateTextGraphics() {
        TextGraphics newTg = gui.createTextGraphics();
        assertNotNull(newTg);
        verify(screen, times(2)).newTextGraphics();
    }

    @Test
    void testGetTerminalDimensions() {
        TerminalSize size = new TerminalSize(80, 24);
        when(screen.getTerminalSize()).thenReturn(size);
        
        assertEquals(80, gui.getTerminalWidth());
        assertEquals(24, gui.getTerminalHeight());
    }
}
