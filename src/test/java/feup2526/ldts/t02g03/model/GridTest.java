package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    @Test
    public void testBuilder(){
        assertThrows(IllegalArgumentException.class, () -> new Grid(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Grid(5, 0));
        assertThrows(IllegalArgumentException.class, () -> new Grid(-5, 5));
        assertThrows(IllegalArgumentException.class, () -> new Grid(5, -5));
    }

    @Test
    public void testGetters(){
        Grid grid = new Grid(5, 6);
        assertEquals(grid.getH(), 6);
        assertEquals(grid.getW(), 5);
    }

    @Test
    public void testIsInside(){
        Grid grid = new Grid(8, 9);

        //Top left corner checks
        assertTrue(grid.isInside(new Position(0,0)));
        assertFalse(grid.isInside(new Position(-1,0)));
        assertFalse(grid.isInside(new Position(0,-1)));
        assertFalse(grid.isInside(new Position(-1,-1)));

        //Bottom right corner checks
        assertTrue(grid.isInside(new Position(7,8)));
        assertFalse(grid.isInside(new Position(7,9)));
        assertFalse(grid.isInside(new Position(8,8)));
        assertFalse(grid.isInside(new Position(8,9)));

        //Random point inside check
        assertTrue(grid.isInside(new Position(5,5)));
    }

    @Test
    public void testClamp(){
        Grid grid = new Grid(8, 9);
        assertEquals(new Position(0,0), grid.clamp(new Position(-5,-9)));
        assertEquals(new Position(7,8), grid.clamp(new Position(99,99)));
        assertEquals(new Position(1,0), grid.clamp(new Position(1,0))); // no change
    }
}
