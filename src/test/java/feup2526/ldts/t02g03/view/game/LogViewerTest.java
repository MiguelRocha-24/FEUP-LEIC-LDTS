package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Log;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class LogViewerTest {
    private LogViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableLogViewer extends LogViewer {
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableLogViewer();
    }

    @Test
    void testDraw() {
        Log log = new Log(new Position(2, 3), feup2526.ldts.t02g03.model.game.Direction.RIGHT);
        
        viewer.draw(mockGUI, log, 20, 60);

        // Correct coordinates logic: x = 2*20 = 40, y = 60 + 3 = 63
        Mockito.verify(mockGUI).drawImage(eq(40), eq(63), eq(mockImage));
    }
}
