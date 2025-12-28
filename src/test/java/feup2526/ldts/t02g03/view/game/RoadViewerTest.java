package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Bus;
import feup2526.ldts.t02g03.model.game.Car;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.game.RoadLane;
import feup2526.ldts.t02g03.model.game.Vehicle;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class RoadViewerTest {
    private RoadViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableRoadViewer extends RoadViewer {
        public TestableRoadViewer(int width) {
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
        viewer = new TestableRoadViewer(10);
    }

    @Test
    void testDraw() {
        RoadLane mockLane = Mockito.mock(RoadLane.class);
        Vehicle car = new Car(new Position(1, 1), Direction.RIGHT);
        Vehicle bus = new Bus(new Position(5, 1), Direction.LEFT);
        List<Vehicle> vehicles = Arrays.asList(car, bus);
        
        Mockito.when(mockLane.getVehicles()).thenReturn(vehicles);
        // Correct mock to accept ints for width/height
        Mockito.when(mockGUI.createOffScreenImage(anyInt(), anyInt())).thenReturn(mockImage);

        viewer.draw(mockGUI, mockLane, 16, 32);

        // Verify background tiles (width 10) + 2 vehicles
        Mockito.verify(mockGUI, Mockito.times(10 + 2)).drawImage(anyInt(), anyInt(), any()); 
    }
}
