package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LaneGenerationManagerTest {
    private Level level;
    private Grid grid;
    private Camera camera;
    private LaneGenerationManager laneGenerationManager;

    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        grid = mock(Grid.class);
        camera = mock(Camera.class);
        when(level.getGrid()).thenReturn(grid);
        when(level.getCamera()).thenReturn(camera);
        when(grid.getH()).thenReturn(20);
        when(grid.getW()).thenReturn(10);
        laneGenerationManager = spy(new LaneGenerationManager(level));
    }

    @Test
    void testInitialization() {
        assertNotNull(laneGenerationManager);
    }

    @Test
    void testUpdateGeneratesLaneWhenNeeded() {
        when(camera.getY()).thenReturn(10.0);
        when(level.getLane(anyInt())).thenReturn(null);
        doReturn(0.5).when(laneGenerationManager).getRandom();
        laneGenerationManager.update();
        verify(level, atLeastOnce()).addLane(anyInt(), any(Lane.class));
    }

    @Test
    void testUpdateDoNotRegenerateExistingLane() {
        when(camera.getY()).thenReturn(16.0);
        when(level.getLane(17)).thenReturn(mock(Lane.class));
        doReturn(0.5).when(laneGenerationManager).getRandom();
        laneGenerationManager.update();
        verify(level, never()).addLane(eq(17), any(Lane.class));
    }

    @Test
    void testUpdateRemovesLanes() {
        when(level.getLane(anyInt())).thenReturn(mock(Lane.class));
        when(camera.getY()).thenReturn(-10.0);
        laneGenerationManager.update();
        verify(level).removeLane(19);
        verify(level, atLeastOnce()).removeLane(13);
        verify(level, never()).removeLane(12);
    }

    @Test
    void testGenerateRoadLane() {
        when(camera.getY()).thenReturn(23.0);
        when(level.getLane(anyInt())).thenReturn(null);
        doReturn(0.2, 0.5, 0.1).when(laneGenerationManager).getRandom();
        laneGenerationManager.update();
        ArgumentCaptor<Lane> laneCaptor = ArgumentCaptor.forClass(Lane.class);
        verify(level).addLane(eq(17), laneCaptor.capture());
        assertTrue(laneCaptor.getValue() instanceof RoadLane);
    }

    @Test
    void testGenerateRiverLane() {
        when(camera.getY()).thenReturn(23.0);
        when(level.getLane(anyInt())).thenReturn(null);
        doReturn(0.8, 0.5, 0.5).when(laneGenerationManager).getRandom();
        laneGenerationManager.update();
        ArgumentCaptor<Lane> laneCaptor = ArgumentCaptor.forClass(Lane.class);
        verify(level).addLane(eq(17), laneCaptor.capture());
        assertTrue(laneCaptor.getValue() instanceof River);
    }

    @Test
    void testGenerateSafeLane() {
        when(camera.getY()).thenReturn(23.0);
        when(level.getLane(anyInt())).thenReturn(null);
        doReturn(0.5, 0.5, 0.8).when(laneGenerationManager).getRandom();
        laneGenerationManager.update();
        ArgumentCaptor<Lane> laneCaptor = ArgumentCaptor.forClass(Lane.class);
        verify(level).addLane(eq(17), laneCaptor.capture());
        assertTrue(laneCaptor.getValue() instanceof SafeLane);
    }
}
