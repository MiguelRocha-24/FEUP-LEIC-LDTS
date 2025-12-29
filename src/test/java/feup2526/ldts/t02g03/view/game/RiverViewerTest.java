package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.River;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RiverViewerTest {
    private static final int TILESIZE = 16;
    private RiverViewer viewer;
    private GUI mockGUI;
    private GUIImage mockRiverImage;

    private class TestableRiverViewer extends RiverViewer {
        private String lastRequestedPath;

        public TestableRiverViewer(int width) {
            super(width);
        }

        @Override
        public GUIImage getSprite(GUI gui, String path) {
            lastRequestedPath = path;
            return mockRiverImage;
        }

        public String getLastRequestedPath() {
            return lastRequestedPath;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = mock(GUI.class);
        mockRiverImage = mock(GUIImage.class);
        viewer = new TestableRiverViewer(10);
    }

    @Test
    void testDrawLeftDirection() {
        River mockRiver = mock(River.class);
        when(mockRiver.getDirection()).thenReturn(Direction.LEFT);
        when(mockRiver.getLogs()).thenReturn(Collections.emptyList());
        viewer.draw(mockGUI, mockRiver, TILESIZE, 48);
        assert "docs/images/sprites/riverLeft.png".equals(((TestableRiverViewer) viewer).getLastRequestedPath());
        // width 10
        verify(mockGUI, times(10)).drawImage(anyInt(), eq(48), eq(mockRiverImage));
    }

    @Test
    void testDrawRightDirection() {
        River mockRiver = mock(River.class);
        when(mockRiver.getDirection()).thenReturn(Direction.RIGHT);
        when(mockRiver.getLogs()).thenReturn(Collections.emptyList());
        viewer.draw(mockGUI, mockRiver, TILESIZE, 48);

        assert "docs/images/sprites/riverRight.png".equals(((TestableRiverViewer) viewer).getLastRequestedPath());
        verify(mockGUI, times(10)).drawImage(anyInt(), eq(48), eq(mockRiverImage));
    }
}
