package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Direction;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.model.Position;
import feup2526.ldts.t02g03.model.RoadLane;
import feup2526.ldts.t02g03.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    private Level level;
    private GameController controller;

    @BeforeEach
    void setUp() {
        level = new Level(10, 10);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        controller = new GameController(level);
    }

    @Test
    void testBuilder() {
        controller = new GameController(level);
        assertNotNull(controller.getLevel());
        assertNotNull(controller.getScanner());
        assertNotNull(controller.getLaneControllers());
    }

    @Test
    void testUpdatePlayerUp() {
        provideInput("W\n");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos.up(), level.getPlayer().getPosition());
    }

    @Test
    void testUpdatePlayerDown() {
        level.getPlayer().tryMove(Direction.UP, level.getGrid());
        Position initialPos = level.getPlayer().getPosition();
        provideInput("S\n");
        controller.updatePlayer();
        assertEquals(initialPos.down(), level.getPlayer().getPosition());
    }

    @Test
    void testUpdatePlayerLeft() {
        provideInput("A\n");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos.left(), level.getPlayer().getPosition());
    }

    @Test
    void testUpdatePlayerRight() {
        provideInput("D\n");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos.right(), level.getPlayer().getPosition());
    }

    @Test
    void testUpdatePlayerQQuit() {
        provideInput("Q\n");
        assertFalse(level.isGameOver());
        controller.updatePlayer();
        assertTrue(level.isGameOver());
    }

    @Test
    void testUpdatePlayerEscQuit() {
        provideInput("ESC\n");
        assertFalse(level.isGameOver());
        controller.updatePlayer();
        assertTrue(level.isGameOver());
    }

    @Test
    void testWrongKey() {
        provideInput("X\n");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos, level.getPlayer().getPosition());
    }

    @Test
    void testNoInput() {
        provideInput("");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos, level.getPlayer().getPosition());
    }

    @Test
    void testNoInput2() {
        provideInput("\n");
        Position initialPos = level.getPlayer().getPosition();
        controller.updatePlayer();
        assertEquals(initialPos, level.getPlayer().getPosition());
    }

    @Test
    void testUpdateLanes() {
        RoadLane lane1 = new RoadLane(Direction.RIGHT, 1, 1);
        RoadLane lane2 = new RoadLane(Direction.LEFT, 1, 2);
        level.getLanes().clear();
        level.getLanes().add(lane1);
        level.getLanes().add(lane2);

        Vehicle v1 = new Vehicle(new Position(5, 1), Direction.RIGHT);
        Vehicle v2 = new Vehicle(new Position(5, 2), Direction.LEFT);
        lane1.addVehicle(v1);
        lane2.addVehicle(v2);

        controller = new GameController(level);
        controller.updateLanes();

        assertEquals(new Position(6, 1), lane1.getVehicles().get(0).getPosition());
        assertEquals(new Position(4, 2), lane2.getVehicles().get(0).getPosition());
    }
}
