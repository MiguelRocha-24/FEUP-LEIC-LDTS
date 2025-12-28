package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DirectionTest {
    @Test
    void testEnum() {
        assertNotNull(Direction.UP);
        assertNotNull(Direction.DOWN);
        assertNotNull(Direction.LEFT);
        assertNotNull(Direction.RIGHT);
        assertEquals(4, Direction.values().length);
    }
}
