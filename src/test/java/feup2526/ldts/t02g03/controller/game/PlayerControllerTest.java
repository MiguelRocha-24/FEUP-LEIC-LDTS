package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    private Player player;
    private PlayerController playerController;
    private Grid grid;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(5, 5));
        playerController = new PlayerController(player);
        grid = new Grid(10, 10);
    }

    @Test
    void testUpdateMovesToTarget() {
        player.setPosition(new Position(0, 0));
        player.setTargetPosition(new Position(10, 0));
        playerController.update();
        assertEquals(new Position(0.5, 0), player.getPosition());
        player.setPosition(new Position(9.9, 0));
        playerController.update();
        assertEquals(new Position(10, 0), player.getPosition());
    }

    @Test
    void testChangeTargetPositionAllDirections() {
        assertTrue(playerController.changeTargetPosition(Direction.UP, grid));
        assertEquals(new Position(5, 4), player.getTargetPosition());
        assertTrue(playerController.changeTargetPosition(Direction.DOWN, grid));
        assertEquals(new Position(5, 5), player.getTargetPosition());
        assertTrue(playerController.changeTargetPosition(Direction.LEFT, grid));
        assertEquals(new Position(4, 5), player.getTargetPosition());
        assertTrue(playerController.changeTargetPosition(Direction.RIGHT, grid));
        assertEquals(new Position(5, 5), player.getTargetPosition());
        assertFalse(playerController.changeTargetPosition(Direction.UP, new Grid(1, 1)));
    }

    @Test
    void testChangeTargetPositionNullDirection() {
        assertThrows(IllegalArgumentException.class, () -> playerController.changeTargetPosition(null, grid));
    }

    @Test
    void testChangeTargetPositionNullGrid() {
        assertThrows(IllegalArgumentException.class, () -> playerController.changeTargetPosition(Direction.UP, null));
    }

    @Test
    void testMoveTarget() {
        player.setTargetPosition(new Position(10, 5));
        playerController.moveTarget(2.5);
        assertEquals(new Position(12.5, 5), player.getTargetPosition());
        assertEquals(new Position(5, 5), player.getPosition());
    }

    @Test
    void testMoveTo() {
        player.setOnLog(true);
        playerController.moveTo(new Position(8, 8));
        assertEquals(new Position(8, 8), player.getTargetPosition());
        assertFalse(player.isOnLog());
    }

    @Test
    void testMoveBody() {
        playerController.moveBody(3.0);
        assertEquals(new Position(8, 5), player.getPosition());
    }

    @Test
    void testGetDistanceToTarget() {
        player.setPosition(new Position(0, 0));
        player.setTargetPosition(new Position(3, 4));
        assertEquals(5.0, playerController.getDistanceToTarget(), 0.001);
    }
}
