package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Camera;
import feup2526.ldts.t02g03.model.game.Grid;
import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.model.game.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class LaneGenerationManagerTest {
    private Level level;
    private Grid grid;
    private Camera camera;
    private LaneGenerationManager laneGenerationManager;

    @BeforeEach
    void setUp() {
        level = Mockito.mock(Level.class);
        grid = Mockito.mock(Grid.class);
        camera = Mockito.mock(Camera.class);

        when(level.getGrid()).thenReturn(grid);
        when(level.getCamera()).thenReturn(camera);
        when(grid.getH()).thenReturn(20);
        when(grid.getW()).thenReturn(10);

        laneGenerationManager = new LaneGenerationManager(level);
    }

    @Test
    void testUpdateGeneratesLanes() {
        // Initial minGeneratedRow = 18
        when(camera.getY()).thenReturn(10.0);
        
        laneGenerationManager.update();

        // Expect lanes to be added
        verify(level, atLeastOnce()).addLane(anyInt(), any(Lane.class));
    }

    @Test
    void testUpdateRemovesLanes() {
        // Simulate camera moving very high up (y decreases)
        when(camera.getY()).thenReturn(-100.0);
        
        laneGenerationManager.update();
        
        // Expect lanes to be removed from the bottom
        verify(level, atLeastOnce()).removeLane(anyInt());
    }
}

