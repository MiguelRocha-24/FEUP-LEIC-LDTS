package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    private Player player;
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(5, 5));
        playerController = new PlayerController(player);
    }

    @Test
    void testMoveTo() {
        Position newTarget = new Position(6, 6);
        playerController.moveTo(newTarget);
        assertEquals(newTarget, player.getTargetPosition());
        assertFalse(player.isOnLog());
    }

    @Test
    void testUpdateMovesTowardsTarget() {
        player.setTargetPosition(new Position(6, 5));
        playerController.update();
        assertEquals(5.5, player.getPosition().getX());
        assertEquals(5.0, player.getPosition().getY());
        playerController.update();
        assertEquals(6.0, player.getPosition().getX());
    }

    @Test
    void testGetDistanceToTarget() {
        player.setPosition(new Position(0, 0));
        player.setTargetPosition(new Position(3, 4));
        assertEquals(5.0, playerController.getDistanceToTarget(), 0.001);
    }
}
