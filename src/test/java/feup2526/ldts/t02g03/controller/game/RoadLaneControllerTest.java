package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Random;

public class RoadLaneControllerTest {
    private RoadLane roadLane = new RoadLane(Direction.LEFT, 1, 1);
    private Grid grid = new Grid(10, 10);
    private Level level = new Level(10, 10);

    @Test
    void testBuilder() {
        // Constructor: double spawnChance, int minSpeed, int maxSpeed, long seed
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
        RoadLaneController RLC = new RoadLaneController(0.3, 1, 1, 1);
        roadLane.addVehicle(new Vehicle(new Position(2, 1), Direction.LEFT));
        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));

        // Manually update logic since update() does both move and spawn
        // We want to test move logic specifically or rely on update
        RLC.update(roadLane, level);

        // Vehicles move 1 unit LEFT (speed 1)
        assertEquals(new Vehicle(new Position(1, 1), Direction.LEFT), roadLane.getVehicles().get(0));
        assertEquals(new Vehicle(new Position(4, 1), Direction.LEFT), roadLane.getVehicles().get(1));
    }

    @Test
    void testCleanupLeft() {
        RoadLaneController RLC = new RoadLaneController(0.0, 1, 1, 1); // 0 spawn chance to isolate cleanup
        roadLane.addVehicle(new Vehicle(new Position(-4, 1), Direction.LEFT));
        
        // Before update (position is exactly boundary)
        assertEquals(1, roadLane.getVehicles().size());

        // Update calls move then remove
        RLC.update(roadLane, level);
        // Vehicle moves to -5, should be removed (< -4)
        assertTrue(roadLane.getVehicles().isEmpty());
    }

    @Test
    void testMaybeSpawnDeterministic() {
        // Seeded random for deterministic behavior
        RoadLaneController controller1 = new RoadLaneController(0.0, 1, 1, 123);
        roadLane.getVehicles().clear();
        for (int i = 0; i < 20; i++) {
            controller1.update(roadLane, level);
            assertTrue(roadLane.getVehicles().isEmpty());
        }

        // High spawn chance
        RoadLaneController controller2 = new RoadLaneController(1.0, 1, 1, 123);
        roadLane.getVehicles().clear();
        controller2.update(roadLane, level);
        // Might spawn or might check space. With empty lane and 1.0 chance, should spawn.
        assertFalse(roadLane.getVehicles().isEmpty());
    }

    @Test
    void testMaybeSpawnMock() {
        Random mockRandom = Mockito.mock(Random.class);
        RoadLaneController controller = new RoadLaneController(0.5, 1, 1, 1);
        controller.setRandom(mockRandom);

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1); // < 0.5 -> Spawn
        // Need subsequent double calls for vehicle type (bus vs car)
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1, 0.5); 
        
        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertFalse(roadLane.getVehicles().isEmpty());

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.6); // > 0.5 -> No spawn
        roadLane.getVehicles().clear();
        controller.update(roadLane, level);
        assertTrue(roadLane.getVehicles().isEmpty());
    }
    
    @Test
    void testGetters(){
        RoadLaneController controller = new RoadLaneController(0.1, 1, 2, 1);
        assertEquals(0.1, controller.getSpawnChance());
        assertEquals(1, controller.getMinSpeed());
        assertEquals(2, controller.getMaxSpeed());
    }
}
