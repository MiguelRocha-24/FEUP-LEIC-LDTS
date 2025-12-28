package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RiverControllerTest {
    private RiverController controller;
    private River river;
    private Level level;

    @BeforeEach
    void setUp() {
        controller = new RiverController(0.5);
        river = new River(1, Direction.RIGHT, 1.0);
        level = new Level(10, 10);
    }

    @Test
    void testConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new RiverController(-0.1));
        assertThrows(IllegalArgumentException.class, () -> new RiverController(1.1));
    }

    @Test
    void testMoveLogs() {
        Log log = new Log(new Position(1, 1), Direction.RIGHT);
        river.addLog(log);
        controller.update(river, level);
        assertEquals(2.0, log.getPosition().getX());
    }

    @Test
    void testCleanupRemovesOutOfBounds() {
        RiverController noSpawnController = new RiverController(0.0);
        Log inBoundsLog = new Log(new Position(5, 1), Direction.RIGHT);
        Log outOfBoundsLog = new Log(new Position(13, 1), Direction.RIGHT);
        river.addLog(inBoundsLog);
        river.addLog(outOfBoundsLog);
        noSpawnController.update(river, level);
        assertEquals(1, river.getLogs().size());
        assertEquals(6.0, river.getLogs().getFirst().getPosition().getX());
    }

    @Test
    void testCleanupLeftDirection() {
        RiverController noSpawnController = new RiverController(0.0);
        River leftRiver = new River(1, Direction.LEFT, 1.0);
        Log inBoundsLog = new Log(new Position(5, 1), Direction.LEFT);
        Log outOfBoundsLog = new Log(new Position(-3, 1), Direction.LEFT);
        leftRiver.addLog(outOfBoundsLog);
        leftRiver.addLog(inBoundsLog);
        noSpawnController.update(leftRiver, level);
        assertEquals(1, leftRiver.getLogs().size());
        assertEquals(4.0, leftRiver.getLogs().getLast().getPosition().getX());
    }

    @Test
    void testGetLogAtBoundaries() {
        Log log = new Log(new Position(5, 1), Direction.RIGHT);
        river.addLog(log);
        // If players drawn on 4.5, its "hitbox" is centered at x = 5, so it should be
        // able to "hit" the log at x = 5
        assertEquals(log, controller.getLogAt(river, new Position(4.5, 1)));
        assertEquals(log, controller.getLogAt(river, new Position(5.0, 1)));
        assertEquals(log, controller.getLogAt(river, new Position(5.5, 1)));
        assertNull(controller.getLogAt(river, new Position(4.4, 1)));
        assertNull(controller.getLogAt(river, new Position(5.6, 1)));
    }

    @Test
    void testGetSnapPositionBoundaries() {
        Log log = new Log(new Position(5, 1), Direction.RIGHT);
        river.addLog(log);
        assertEquals(new Position(5.0, 1.0), controller.getSnapPosition(river, new Position(4.5, 1)));
        assertEquals(new Position(5.0, 1.0), controller.getSnapPosition(river, new Position(5.0, 1)));
        assertEquals(new Position(5.0, 1.0), controller.getSnapPosition(river, new Position(5.5, 1)));
        assertEquals(new Position(4.4, 1.0), controller.getSnapPosition(river, new Position(4.4, 1)));
        assertEquals(new Position(5.6, 1.0), controller.getSnapPosition(river, new Position(5.6, 1)));
    }

    @Test
    void testIsBlocked() {
        // always available, either death / alive, not unable to move there
        assertFalse(controller.isBlocked(river, new Position(5, 5)));
    }

    @Test
    void testHandlePhysicsPlayerOnLog() {
        Level mockLevel = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        Grid mockGrid = mock(Grid.class);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockLevel.getGrid()).thenReturn(mockGrid);
        when(mockGrid.getW()).thenReturn(10);
        when(mockPlayer.getPosition()).thenReturn(new Position(5.5, 1));
        Log log = new Log(new Position(5, 1), Direction.RIGHT);
        river.addLog(log);
        controller.handlePhysics(river, mockLevel, new Position(5.5, 1), true);

        verify(mockPlayer).setPosition(new Position(5.0, 1.0));
        verify(mockLevel, never()).handleCollision();
    }

    @Test
    void testHandlePhysicsPlayerOffLogCausesCollision() {
        Level mockLevel = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(new Position(5, 1));
        when(mockPlayer.getTargetPosition()).thenReturn(new Position(5, 1));
        controller.handlePhysics(river, mockLevel, new Position(5, 1), true);

        verify(mockLevel).handleCollision();
    }

    @Test
    void testHandlePhysicsTargetOnLog() {
        Level mockLevel = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getTargetPosition()).thenReturn(new Position(5.5, 1));
        Log log = new Log(new Position(5, 1), Direction.RIGHT);
        river.addLog(log);
        controller.handlePhysics(river, mockLevel, new Position(5.5, 1), false);

        verify(mockPlayer).setTargetPosition(new Position(5.0, 1.0));
        verify(mockLevel, never()).handleCollision();
    }

    @Test
    void testHandlePhysicsPlayerOutOfBounds() {
        Level mockLevel = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        Grid mockGrid = mock(Grid.class);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockLevel.getGrid()).thenReturn(mockGrid);
        when(mockGrid.getW()).thenReturn(10);
        when(mockPlayer.getPosition()).thenReturn(new Position(-2, 1));
        Log log = new Log(new Position(-2, 1), Direction.RIGHT);
        river.addLog(log);
        controller.handlePhysics(river, mockLevel, new Position(-2, 1), true);

        verify(mockLevel).handleCollision();
    }

    @Test
    void testHandlePhysicsPlayerOffLogFarFromTarget() {
        Level mockLevel = mock(Level.class);
        Player mockPlayer = mock(Player.class);
        when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(new Position(5, 1));
        when(mockPlayer.getTargetPosition()).thenReturn(new Position(10, 1));
        controller.handlePhysics(river, mockLevel, new Position(5, 1), true);

        verify(mockLevel, never()).handleCollision();
    }
}
