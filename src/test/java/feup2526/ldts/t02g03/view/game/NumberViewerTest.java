package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

class NumberViewerTest {
    private NumberViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableNumberViewer extends NumberViewer {
        @Override
        protected GUIImage getSprite(GUI gui, int number) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        Mockito.when(mockImage.getWidth()).thenReturn(10);
        viewer = new TestableNumberViewer();
    }

    @Test
    void testDrawSingleDigit() {
        viewer.draw(mockGUI, 1, 10, 10);
        
        // Should draw digit '1' at (10, 10)
        Mockito.verify(mockGUI).drawImage(eq(10), eq(10), eq(mockImage));
    }
    
    @Test
    void testDrawMultipleDigits() {
        viewer.draw(mockGUI, 12, 10, 10);
        
        // Should draw '1' at (10,10) and '2' at (10+width, 10)
        // Since mockImage width is 10
        Mockito.verify(mockGUI).drawImage(eq(10), eq(10), eq(mockImage));
        Mockito.verify(mockGUI).drawImage(eq(20), eq(10), eq(mockImage));
    }
}
