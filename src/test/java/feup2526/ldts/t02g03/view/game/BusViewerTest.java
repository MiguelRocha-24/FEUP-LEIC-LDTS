package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Bus;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;

class BusViewerTest {
    private static final int TILESIZE = 16;
    private BusViewer viewer;
    private GUI mockGUI;
    private GUIImage mockLeftImage;
    private GUIImage mockRightImage;

    private class TestableBusViewer extends BusViewer {
        private String lastRequestedPath;

        @Override
        public GUIImage getSprite(GUI gui, String path) {
            lastRequestedPath = path;
            return path.equals("docs/images/sprites/Bus-Left.png") ? mockLeftImage : mockRightImage;
        }

        public String getLastRequestedPath() {
            return lastRequestedPath;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockLeftImage = Mockito.mock(GUIImage.class);
        mockRightImage = Mockito.mock(GUIImage.class);
        viewer = new TestableBusViewer();
    }

    @Test
    void testDrawLeft() {
        Bus bus = new Bus(new Position(10, 5), Direction.LEFT);
        viewer.draw(mockGUI, bus, TILESIZE, 80);
        assert "docs/images/sprites/Bus-Left.png".equals(((TestableBusViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(160), eq(80), eq(mockLeftImage));
    }

    @Test
    void testDrawRight() {
        Bus bus = new Bus(new Position(10, 5), Direction.RIGHT);
        viewer.draw(mockGUI, bus, TILESIZE, 80);
        assert "docs/images/sprites/Bus-Right.png".equals(((TestableBusViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(160), eq(80), eq(mockRightImage));
    }
}
