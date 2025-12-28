package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Bus;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class BusViewerTest {
    private BusViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableBusViewer extends BusViewer {
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableBusViewer();
    }

    @Test
    void testDrawLeft() {
        Bus bus = new Bus(new Position(10, 5), Direction.LEFT);
        
        viewer.draw(mockGUI, bus, 16, 80);

        Mockito.verify(mockGUI).drawImage(eq(160), eq(80), eq(mockImage));
    }

    @Test
    void testDrawRight() {
        Bus bus = new Bus(new Position(10, 5), Direction.RIGHT);
        
        viewer.draw(mockGUI, bus, 16, 80);

        Mockito.verify(mockGUI).drawImage(eq(160), eq(80), eq(mockImage));
    }
}
