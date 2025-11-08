package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    @Test
    void constructorTester() {
        Player p = new Player(new Position(1, 1), Direction.LEFT);
        assertEquals(new Position(1,1), p.getPosition());
        assertEquals(Direction.LEFT, p.getDirection());
    }

    @Test
    void moveTester() {
        Grid g = new Grid(3,3);
        Player p = new Player(new Position(1,1), Direction.UP);

        assertTrue(p.tryMove(Direction.UP, g));
        assertEquals(new Position(1,0), p.getPosition());
        assertEquals(Direction.UP, p.getDirection());

        assertTrue(p.tryMove(Direction.RIGHT, g));
        assertEquals(new Position(2,0), p.getPosition());
        assertEquals(Direction.RIGHT, p.getDirection());

        assertTrue(p.tryMove(Direction.DOWN, g));
        assertEquals(new Position(2,1), p.getPosition());
        assertEquals(Direction.DOWN, p.getDirection());

        assertTrue(p.tryMove(Direction.LEFT, g));
        assertEquals(new Position(1,1), p.getPosition());
        assertEquals(Direction.LEFT, p.getDirection());
    }

    @Test
    void moveTester2() {
        Grid g = new Grid(3,3);

        Player leftEdge = new Player(new Position(0,1), Direction.RIGHT);
        assertFalse(leftEdge.tryMove(Direction.LEFT, g));
        assertEquals(new Position(0,1), leftEdge.getPosition());

        Player topEdge = new Player(new Position(1,0), Direction.DOWN);
        assertFalse(topEdge.tryMove(Direction.UP, g));
        assertEquals(new Position(1,0), topEdge.getPosition());

        Player rightEdge = new Player(new Position(2,1), Direction.LEFT);
        assertFalse(rightEdge.tryMove(Direction.RIGHT, g));
        assertEquals(new Position(2,1), rightEdge.getPosition());

        Player bottomEdge = new Player(new Position(1,2), Direction.UP);
        assertFalse(bottomEdge.tryMove(Direction.DOWN, g));
        assertEquals(new Position(1,2), bottomEdge.getPosition());
    }

    @Test
    void builderTester() {
        Grid g = new Grid(3,3);
        Player p = new Player(new Position(1,1));

        try {
            p.tryMove(null, g);
            fail("Expected IllegalArgumentException when direction is null");
        }
        catch (IllegalArgumentException e) {

        }

        try{
            p.tryMove(Direction.UP, null);
            fail("Expected IllegalArgumentException when grid is null");
        }
        catch (IllegalArgumentException e) {

        }
    }
}