package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameViewerTest {

    private static class TestableGameViewerWithGetters extends GameViewer {
        private PlayerViewer generatedPlayerViewer;
        private NumberViewer generatedNumberViewer;
        private Map<Class<?>, LaneViewer> generatedViewerMap;

        public TestableGameViewerWithGetters(Level model) {
            super(model);
        }

        @Override
        protected PlayerViewer createPlayerViewer() {
            generatedPlayerViewer = Mockito.mock(PlayerViewer.class);
            return generatedPlayerViewer;
        }

        @Override
        protected NumberViewer createNumberViewer() {
            generatedNumberViewer = Mockito.mock(NumberViewer.class);
            return generatedNumberViewer;
        }

        @Override
        protected Map<Class<?>, LaneViewer> createViewerMap(int width) {
            if (generatedViewerMap == null) {
                generatedViewerMap = new HashMap<>();
            }
            // We need to return a map that produces mocks for known classes
            generatedViewerMap.put(RoadLane.class, Mockito.mock(RoadViewer.class));
            generatedViewerMap.put(River.class, Mockito.mock(RiverViewer.class));
            generatedViewerMap.put(SafeLane.class, Mockito.mock(SafeLaneViewer.class));
            return generatedViewerMap;
        }

        public PlayerViewer getMockPlayerViewer() { return generatedPlayerViewer; }
        public NumberViewer getMockNumberViewer() { return generatedNumberViewer; }
        public LaneViewer getMockLaneViewer(Class<?> clazz) { return generatedViewerMap.get(clazz); }
    }

    @Test
    void testDrawElements() throws IOException {
        // Arrange
        Level mockLevel = Mockito.mock(Level.class);
        Grid mockGrid = Mockito.mock(Grid.class);
        Camera mockCamera = Mockito.mock(Camera.class);
        Player mockPlayer = Mockito.mock(Player.class);
        RunScore mockRunScore = Mockito.mock(RunScore.class);
        Position mockPos = new Position(10, 10);

        when(mockLevel.getGrid()).thenReturn(mockGrid);
        when(mockGrid.getW()).thenReturn(30);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockLevel.getRunScore()).thenReturn(mockRunScore);
        when(mockLevel.isCollisionDetected()).thenReturn(true);
        when(mockLevel.getCollisionTime()).thenReturn(12345L);
        when(mockPlayer.getPosition()).thenReturn(mockPos);
        when(mockRunScore.getCount()).thenReturn(42);
        
        when(mockCamera.getY()).thenReturn(5.0); // Camera at
        RoadLane road = new RoadLane(Direction.RIGHT, 1.0, 5);
        River river = new River(6, Direction.LEFT, 1.0);
        
        when(mockLevel.getLane(anyInt())).thenReturn(null);
        
        GUI mockGUI = Mockito.mock(GUI.class);
        when(mockGUI.getTerminalHeight()).thenReturn(160);
        when(mockGUI.getTerminalWidth()).thenReturn(100);
        when(mockLevel.getLane(5)).thenReturn(road);
        when(mockLevel.getLane(6)).thenReturn(river);

        TestableGameViewerWithGetters viewer = new TestableGameViewerWithGetters(mockLevel);
        
        when(viewer.getMockNumberViewer().getWidth(any(GUI.class), anyInt())).thenReturn(5);

        viewer.draw(mockGUI);


        verify(viewer.getMockPlayerViewer()).draw(
            eq(mockGUI), 
            eq(mockPlayer), 
            eq(16), 
            anyInt(),
            eq(true),
            eq(12345L)
        );

        verify(viewer.getMockNumberViewer()).draw(eq(mockGUI), eq(42), anyInt(), eq(1));

        LaneViewer mockRoadViewer = viewer.getMockLaneViewer(RoadLane.class);
        LaneViewer mockRiverViewer = viewer.getMockLaneViewer(River.class);
        
        verify(mockRoadViewer).draw(eq(mockGUI), eq(road), eq(16), anyInt());
        verify(mockRoadViewer).draw(eq(mockGUI), eq(road), eq(16), anyInt());
        verify(mockRiverViewer).draw(eq(mockGUI), eq(river), eq(16), anyInt());
    }

    @Test
    void testSetPlayerSkin() {
        Level mockLevel = Mockito.mock(Level.class);
        when(mockLevel.getGrid()).thenReturn(Mockito.mock(Grid.class));
        
        TestableGameViewerWithGetters viewer = new TestableGameViewerWithGetters(mockLevel);
        
        viewer.setPlayerSkin("newSkin");
        
        verify(viewer.getMockPlayerViewer()).setSkinName("newSkin");
    }
}
