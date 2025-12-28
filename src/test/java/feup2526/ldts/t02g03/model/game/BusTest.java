package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusTest {
    @Test
    void testBusCreation() {
        Bus bus = new Bus(new Position(1, 1), Direction.LEFT);
        assertEquals(2.6, bus.getWidth());
        assertEquals(new Position(1, 1), bus.getPosition());
        assertEquals(Direction.LEFT, bus.getDirection());
    }
}
