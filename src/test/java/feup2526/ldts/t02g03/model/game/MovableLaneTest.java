package feup2526.ldts.t02g03.model.game;
import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.MovableLane;
import feup2526.ldts.t02g03.model.game.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class MovableLaneTest{
    private class TestMovableLane extends MovableLane {
        public TestMovableLane(int row, Direction direction, int speed) {
            super(row, direction, speed);
        }
    }

    @Test
    void testBuilder(){
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {new TestMovableLane(1, null, -1);});
        assertTrue(thrown2.getMessage().contains("Lane"));

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {new TestMovableLane(1, Direction.UP, -1);});
        assertTrue(thrown3.getMessage().contains("Lane"));
        IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {new TestMovableLane(1, Direction.LEFT, 0);});
        assertTrue(thrown4.getMessage().contains("Speed"));
    }

    @Test
    void testGetters(){
        TestMovableLane lane = new TestMovableLane(1, Direction.LEFT, 1);
        assertEquals(1, lane.getRow());
        assertEquals(Direction.LEFT, lane.getDirection());
        assertEquals(1, lane.getSpeed());
    }
}
