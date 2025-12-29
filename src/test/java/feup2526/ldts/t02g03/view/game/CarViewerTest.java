package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Car;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

class CarViewerTest {
    private static final int TILESIZE = 16;
    private CarViewer viewer;
    private GUI mockGUI;
    private GUIImage mockLeftImage;
    private GUIImage mockRightImage;

    private class TestableCarViewer extends CarViewer {
        private String lastRequestedPath;

        @Override
        public GUIImage getSprite(GUI gui, String path) {
            lastRequestedPath = path;
            return path.equals("docs/images/sprites/carLeft.png") ? mockLeftImage : mockRightImage;
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
        viewer = new TestableCarViewer();
    }

    //Coords in both of tests account for the small drawing offset added beacause sprites aren't
    //full 16x16 squares
    @Test
    void testDrawLeft() {
        Car mockCar = Mockito.mock(Car.class);
        Mockito.when(mockCar.getPosition()).thenReturn(new Position(5, 5));
        Mockito.when(mockCar.getDirection()).thenReturn(Direction.LEFT);
        viewer.draw(mockGUI, mockCar, TILESIZE, 80);
        assert "docs/images/sprites/carLeft.png".equals(((TestableCarViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(82), eq(81), eq(mockLeftImage));
    }

    @Test
    void testDrawRight() {
        Car mockCar = Mockito.mock(Car.class);
        Mockito.when(mockCar.getPosition()).thenReturn(new Position(5, 5));
        Mockito.when(mockCar.getDirection()).thenReturn(Direction.RIGHT);
        viewer.draw(mockGUI, mockCar, TILESIZE, 80);
        assert "docs/images/sprites/carRight.png".equals(((TestableCarViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(82), eq(81), eq(mockRightImage));
    }
}
