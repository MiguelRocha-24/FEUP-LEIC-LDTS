package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    @Test
    void testLogCreation() {
        Log log = new Log(new Position(1, 1), Direction.RIGHT);
        assertEquals(1.0, log.getWidth());
        assertEquals(0.0, log.getOffsetX());
        assertEquals(new Position(1, 1), log.getPosition());
    }
}
