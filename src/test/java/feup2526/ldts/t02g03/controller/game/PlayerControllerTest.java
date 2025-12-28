package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    @Test
    void testUpdateMovesToTarget() {
        Player p = new Player(new Position(0, 0));
        PlayerController pc = new PlayerController(p);
        
        p.setTargetPosition(new Position(10, 0));
        // Speed is 0.5 per update
        pc.update();
        assertEquals(new Position(0.5, 0), p.getPosition());
        
        // Closer than speed
        p.setPosition(new Position(9.9, 0));
        pc.update();
        assertEquals(new Position(10, 0), p.getPosition());
    }

    @Test
    void testChangeTargetPosition() {
        Player p = new Player(new Position(5, 5));
        Grid grid = new Grid(10, 10);
        PlayerController pc = new PlayerController(p);
        
        assertTrue(pc.changeTargetPosition(Direction.UP, grid));
        assertEquals(new Position(5, 4), p.getTargetPosition());
        
        assertFalse(pc.changeTargetPosition(Direction.UP, new Grid(1, 1))); // Out of bounds
    }
}
