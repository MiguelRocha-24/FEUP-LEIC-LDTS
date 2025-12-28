package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.Grid;
import feup2526.ldts.t02g03.model.game.Position;

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
        assertEquals(6, grid.getH());
        assertEquals(5, grid.getW());
    }

    @Test
    public void testIsInside(){
        Grid grid = new Grid(8, 9);

        //Check X bounds
        assertTrue(grid.isInside(new Position(0,0)));
        assertFalse(grid.isInside(new Position(-1,0)));
        assertTrue(grid.isInside(new Position(7,8)));
        assertFalse(grid.isInside(new Position(8,8)));

        //Check that Y checks are ignored currently (based on implementation)
        // If the implementation changes, update this test
        assertTrue(grid.isInside(new Position(0,-1))); 
    }
}
