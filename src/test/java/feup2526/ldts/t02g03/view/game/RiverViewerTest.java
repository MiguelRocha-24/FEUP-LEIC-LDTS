package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Log;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.game.River;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class RiverViewerTest {
    private RiverViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableRiverViewer extends RiverViewer {
        public TestableRiverViewer(int width) {
            super(width);
        }
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableRiverViewer(10);
    }

    @Test
    void testDraw() {
        River mockRiver = Mockito.mock(River.class);
        Log log = new Log(new Position(2, 2), Direction.RIGHT);
        List<Log> logs = Collections.singletonList(log);

        Mockito.when(mockRiver.getDirection()).thenReturn(Direction.RIGHT);
        Mockito.when(mockRiver.getLogs()).thenReturn(logs);
        Mockito.when(mockGUI.createOffScreenImage(anyInt(), anyInt())).thenReturn(mockImage);

        viewer.draw(mockGUI, mockRiver, 16, 48);

        // Verify background tiles (width 10) + 1 log
        // Using anyInt() for y because River draws at 48 and LogViewer might draw at 48+offset
        Mockito.verify(mockGUI, Mockito.times(10 + 1)).drawImage(anyInt(), anyInt(), any());
    }
}
