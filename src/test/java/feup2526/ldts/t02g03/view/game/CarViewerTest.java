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
import static org.mockito.Mockito.*;

class CarViewerTest {
    private CarViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableCarViewer extends CarViewer {
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableCarViewer();
    }

    @Test
    void testDraw() {
        Car mockCar = Mockito.mock(Car.class);
        Mockito.when(mockCar.getPosition()).thenReturn(new Position(5, 5));
        Mockito.when(mockCar.getDirection()).thenReturn(Direction.RIGHT);
        
        viewer.draw(mockGUI, mockCar, 10, 50);
        
        // Correct coordinates logic: x = 5*10 + 2 = 52, y = 50 + 1 = 51
        Mockito.verify(mockGUI).drawImage(eq(52), eq(51), eq(mockImage));
    }
}
