package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Random;

public class RoadLaneControllerTest {
    private RoadLane roadLane = new RoadLane(Direction.LEFT, 1, 1);
    private Grid grid = new Grid(10, 10);

    @Test
    void testBuilder() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(null, grid, 0.30, 1, 1, 1);
        });
        assertTrue(thrown.getMessage().contains("Lane"));

        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, null, 0.30, 1, 1, 1);
        });
        assertTrue(thrown2.getMessage().contains("Grid"));

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, grid, -0.3, 1, 1, 1);
        });
        assertTrue(thrown3.getMessage().contains("Spawn"));

        IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, grid, 1.3, 1, 1, 1);
        });
        assertTrue(thrown4.getMessage().contains("Spawn"));

        IllegalArgumentException thrown5 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, grid, 0.3, 0, 1, 1);
        });
        assertTrue(thrown5.getMessage().contains("Min gap"));

        IllegalArgumentException thrown6 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, grid, 0.3, 1, 0, 1);
        });
        assertTrue(thrown6.getMessage().contains("Remove"));

        IllegalArgumentException thrown7 = assertThrows(IllegalArgumentException.class, () -> {
            new RoadLaneController(roadLane, grid, 0.3, 1, 1, 0);
        });
        assertTrue(thrown7.getMessage().contains("offset"));
    }

    @Test
    void testMoveVehicle() {
        RoadLaneController RLC = new RoadLaneController(roadLane, grid, 0.3, 1, 1, 1);
        roadLane.addVehicle(new Vehicle(new Position(2, 1), Direction.LEFT));
        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));
        RLC.step();
        assertEquals(new Vehicle(new Position(1, 1), Direction.LEFT), roadLane.getVehicles().get(0));
        assertEquals(new Vehicle(new Position(4, 1), Direction.LEFT), roadLane.getVehicles().get(1));

    }

    @Test
    void testCleanupLeft() {
        RoadLaneController RLC = new RoadLaneController(roadLane, grid, 0.3, 1, 1, 1);
        roadLane.addVehicle(new Vehicle(new Position(2, 1), Direction.LEFT));
        roadLane.addVehicle(new Vehicle(new Position(5, 1), Direction.LEFT));
        RLC.step();
        RLC.step();
        RLC.step();
        RLC.step();
        // leftmost vehicle should be removed
        assertEquals(new Vehicle(new Position(1, 1), Direction.LEFT), roadLane.getVehicles().get(0));
    }

    @Test
    void testCleanupRight() {
        RoadLane rl = new RoadLane(Direction.RIGHT, 1, 1);
        RoadLaneController RLC = new RoadLaneController(rl, grid, 0, 1, 1, 1);
        rl.addVehicle(new Vehicle(new Position(2, 1), Direction.RIGHT));
        rl.addVehicle(new Vehicle(new Position(5, 1), Direction.RIGHT));

        for (int i = 5; i < grid.getW() + RLC.getRemoveBuffer() + 1; i++) {
            RLC.step();
        }
        // rightmost vehicle should have been removed
        assertEquals(rl.getVehicles().size(), 1);

    }

    @Test
    void testMaybeSpawnDeterministic() {
        RoadLaneController controller1 = new RoadLaneController(roadLane, grid, 0.0, 1, 1, 1);
        roadLane.getVehicles().clear();
        for (int i = 0; i < 100; i++) {
            controller1.step();
            assertTrue(roadLane.getVehicles().isEmpty());
        }

        RoadLaneController controller2 = new RoadLaneController(roadLane, grid, 1.0, 1, 1, 1);
        roadLane.getVehicles().clear();
        controller2.step();
        assertEquals(1, roadLane.getVehicles().size());
    }

    @Test
    void testMaybeSpawnMock() {
        Random mockRandom = Mockito.mock(Random.class);
        RoadLaneController controller = new RoadLaneController(roadLane, grid, 0.3, 1, 1, 1);
        controller.setRandom(mockRandom);

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.1);
        roadLane.getVehicles().clear();
        controller.step();
        assertFalse(roadLane.getVehicles().isEmpty());

        Mockito.when(mockRandom.nextDouble()).thenReturn(0.5);
        roadLane.getVehicles().clear();
        controller.step();
        assertTrue(roadLane.getVehicles().isEmpty());
    }

    @Test
    void testIsSpaceForSpawnRight() {
        RoadLane roadLane = new RoadLane(Direction.RIGHT, 1, 1);
        RoadLaneController controller = new RoadLaneController(roadLane, grid, 0.5, 1, 1, 1);
        Random mockRandom = Mockito.mock(Random.class);
        controller.setRandom(mockRandom);
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.0);
        roadLane.addVehicle(new Vehicle(new Position(-1, 1), Direction.RIGHT));

        //Should not spawn, no space
        controller.step();
        assertEquals(1, roadLane.getVehicles().size());

        //Should spawn, space
        controller.step();
        assertEquals(2, roadLane.getVehicles().size());
    }



    @Test
    void testIsSpaceForSpawnLeft() {
        RoadLane roadLane = new RoadLane(Direction.LEFT, 1, 1);
        RoadLaneController controller = new RoadLaneController(roadLane, grid, 0.5, 1, 1, 1);
        Random mockRandom = Mockito.mock(Random.class);
        controller.setRandom(mockRandom);
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.0);
        roadLane.addVehicle(new Vehicle(new Position(11, 1), Direction.LEFT));

        //Should not spawn, no space
        controller.step();
        assertEquals(1, roadLane.getVehicles().size());

        //Should spawn, space
        controller.step();
        assertEquals(2, roadLane.getVehicles().size());
    }

    //Getters
    @Test
    void testGetters(){
        RoadLane lane = new RoadLane(Direction.LEFT,1,1);
        Grid grid = new Grid(10, 10);
        RoadLaneController controller = new RoadLaneController(lane, grid, 0.1, 1, 1, 1);
        assertSame(lane, controller.getLane());
        assertSame(grid, controller.getGrid());
        assertEquals(0.1, controller.getSpawnChance());
        assertEquals(1, controller.getMinGap());
        assertEquals(1, controller.getRemoveBuffer());
        assertEquals(1, controller.getSpawnOffset());
    }
}
