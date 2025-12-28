package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Random;

public class RoadLaneControllerTest {
    private RoadLane roadLane = new RoadLane(Direction.LEFT, 1, 1);
    private Level level = new Level(10, 10);

    @Test
    void testBuilder() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(-0.1, 1, 1, 1);
        });
        assertTrue(thrown.getMessage().contains("Spawn chance"));

        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(1.1, 1, 1, 1);
        });
        assertTrue(thrown2.getMessage().contains("Spawn chance"));

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(0.5, 0, 1, 1);
        });
        assertTrue(thrown3.getMessage().contains("Invalid speed"));

        IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(0.5, 2, 1, 1);
        });
        assertTrue(thrown4.getMessage().contains("Invalid speed"));
    }

    @Test
    void testMoveVehicle() {
        RoadLaneController RLC = new RoadLaneController(0.0, 1, 1, 1);
        roadLane.addVehicle(new Vehicle(new Position(2, 1), Direction.LEFT));
        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));
        RLC.update(roadLane, level);
        assertEquals(new Vehicle(new Position(1, 1), Direction.LEFT), roadLane.getVehicles().get(0));
        assertEquals(new Vehicle(new Position(4, 1), Direction.LEFT), roadLane.getVehicles().get(1));
    }

    @Test
    void testCleanupLeft() {
        RoadLaneController RLC = new RoadLaneController(0.0, 1, 1, 1); // 0 spawn chance to isolate cleanup
        roadLane.addVehicle(new Vehicle(new Position(-4, 1), Direction.LEFT));
        assertEquals(1, roadLane.getVehicles().size());
        RLC.update(roadLane, level);
        assertTrue(roadLane.getVehicles().isEmpty());
    }

    @Test
    void testMaybeSpawn() {
        Random mockRandom = Mockito.mock(Random.class);
        RoadLaneController controller = new RoadLaneController(0.5, 1, 1, 1);
        controller.setRandom(mockRandom);
        // Less than means spawn, more means no spawn

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1, 0.5);
        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertFalse(roadLane.getVehicles().isEmpty());

        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertTrue(roadLane.getVehicles().isEmpty());
    }

    @Test
    void testHandleCollisionDetected() {
        Level mockLevel = Mockito.mock(Level.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Mockito.when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockPlayer.getPosition()).thenReturn(new Position(5, 1));
        Mockito.when(mockPlayer.getOffsetX()).thenReturn(0.0);
        Mockito.when(mockPlayer.getWidth()).thenReturn(1.0);

        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));
        RoadLaneController controller = new RoadLaneController(0.5, 1, 1, 1);
        controller.handleCollision(roadLane, mockLevel);

        Mockito.verify(mockLevel).handleCollision();
    }

    @Test
    void testHandleCollisionNotDetected() {
        RoadLaneController controller = new RoadLaneController(0.5, 1, 1, 1);
        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));

        // Wrong row - no collision
        Level mockLevel1 = Mockito.mock(Level.class);
        Player mockPlayer1 = Mockito.mock(Player.class);
        Mockito.when(mockLevel1.getPlayer()).thenReturn(mockPlayer1);
        Mockito.when(mockPlayer1.getPosition()).thenReturn(new Position(5, 5));
        controller.handleCollision(roadLane, mockLevel1);
        Mockito.verify(mockLevel1, Mockito.never()).handleCollision();

        // No x overlap - no collision
        Level mockLevel2 = Mockito.mock(Level.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        Mockito.when(mockLevel2.getPlayer()).thenReturn(mockPlayer2);
        Mockito.when(mockPlayer2.getPosition()).thenReturn(new Position(8, 1));
        Mockito.when(mockPlayer2.getOffsetX()).thenReturn(0.0);
        Mockito.when(mockPlayer2.getWidth()).thenReturn(1.0);
        controller.handleCollision(roadLane, mockLevel2);
        Mockito.verify(mockLevel2, Mockito.never()).handleCollision();
    }

    @Test
    void testSpawnVehicles() {
        Random mockRandom = Mockito.mock(Random.class);
        RoadLaneController controller = new RoadLaneController(0.5, 1, 1, 1);
        controller.setRandom(mockRandom);

        // Spawn Car
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1, 0.5);
        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertEquals(1, roadLane.getVehicles().size());
        assertTrue(roadLane.getVehicles().get(0) instanceof Car);

        // Spawn Bus
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1, 0.05);
        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertEquals(1, roadLane.getVehicles().size());
        assertTrue(roadLane.getVehicles().get(0) instanceof Bus);

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1, 0.2);
        controller.update(roadLane, level);
        assertEquals(1, roadLane.getVehicles().size());
    }

    @Test
    void testGetters() {
        RoadLaneController controller = new RoadLaneController(0.1, 1, 2, 1);
        assertEquals(0.1, controller.getSpawnChance());
        assertEquals(1, controller.getMinSpeed());
        assertEquals(2, controller.getMaxSpeed());
    }
}
