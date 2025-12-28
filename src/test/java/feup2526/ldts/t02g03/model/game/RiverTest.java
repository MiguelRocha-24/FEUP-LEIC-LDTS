package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RiverTest {
    private River river;

    @BeforeEach
    void setUp() {
        river = new River(1, Direction.RIGHT, 1.0);
    }

    @Test
    void testInitialization() {
        assertNotNull(river.getLogs());
        assertTrue(river.getLogs().isEmpty());
    }

    @Test
    void testAddLog() {
        Log log = new Log(new Position(1, 1), Direction.RIGHT);
        river.addLog(log);
        assertEquals(1, river.getLogs().size());
        assertEquals(log, river.getLogs().get(0));
    }

    @Test
    void testAddLogOrdering() {
        Log l1 = new Log(new Position(1, 1), Direction.RIGHT);
        Log l2 = new Log(new Position(3, 1), Direction.RIGHT);
        Log l3 = new Log(new Position(2, 1), Direction.RIGHT);

        river.addLog(l1);
        river.addLog(l2);
        river.addLog(l3);

        assertEquals(l1, river.getLogs().get(0));
        assertEquals(l3, river.getLogs().get(1));
        assertEquals(l2, river.getLogs().get(2));
    }

    @Test
    void testInvalidAdd() {
        assertThrows(IllegalArgumentException.class, () -> river.addLog(null));
        assertThrows(IllegalArgumentException.class, () -> river.addLog(new Log(new Position(1, 2), Direction.RIGHT))); // Wrong row
        assertThrows(IllegalArgumentException.class, () -> river.addLog(new Log(new Position(1, 1), Direction.LEFT))); // Wrong direction
    }

    @Test
    void testRemoveLog() {
        Log l = new Log(new Position(1, 1), Direction.RIGHT);
        river.addLog(l);
        river.removeLog(l);
        assertTrue(river.getLogs().isEmpty());
    }

    @Test
    void testUpdate() {
        Log l = new Log(new Position(1, 1), Direction.RIGHT);
        river.addLog(l);
        river.update(); // Expects log to move
        // River update calls log.move(speed). Speed is 1.0, Dir is RIGHT.
        // Log is MovableEntity. move() updates position.
        // Assuming move() works as per MovableEntity implementation (checked previously)
        // Manual verification of Log movement via River update is implicit if Log works.
        // We can check if position changed.
        assertNotEquals(1.0, l.getPosition().getX()); 
    }
}
