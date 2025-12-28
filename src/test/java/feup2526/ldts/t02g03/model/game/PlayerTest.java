package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void constructorTester() {
        Player p = new Player(new Position(1, 1), Direction.LEFT);
        assertEquals(new Position(1,1), p.getPosition());
        assertEquals(Direction.LEFT, p.getDirection());
        assertEquals(new Position(1,1), p.getTargetPosition());
    }

    @Test
    void targetPositionTester() {
        Player p = new Player(new Position(1, 1));
        p.setTargetPosition(new Position(2, 2));
        assertEquals(new Position(2, 2), p.getTargetPosition());
    }

    @Test
    void logTester() {
         Player p = new Player(new Position(1, 1));
         assertFalse(p.isOnLog());
         p.setOnLog(true);
         assertTrue(p.isOnLog());
    }
}
