package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    @Test
    void translateTester() {
        Position p = new Position(2, 3);
        assertEquals(new Position(5, 9), p.translate(3, 6));
        assertEquals(new Position(-1, 1), p.translate(-3, -2));
    }

    @Test
    void upTester(){
        Position p = new Position(0, 2);
        assertEquals(new Position(0, 1), p.up());
    }

    @Test
    void downTester(){
        Position p = new Position(0, 2);
        assertEquals(new Position(0, 3), p.down());
    }

    @Test
    void leftTester(){
        Position p = new Position(2, 0);
        assertEquals(new Position(1, 0), p.left());
    }

    @Test
    void rightTester(){
        Position p = new Position(2, 0);
        assertEquals(new Position(3, 0), p.right());
    }

    @Test
    void getXTester(){
        Position p = new Position(2, 0);
        assertEquals(2, p.getX());
    }

    @Test
    void getYTester(){
        Position p = new Position(2, 0);
        assertEquals(0, p.getY());
    }

    @Test
    void equalityTester(){
        Position p1 = new Position(2, 0);
        Position p2 = new Position(2, 0);
        Position p3 = new Position(2, 1);
        assertEquals(p1, p2);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "teste");
        assertEquals(p1,p1);
        assertNotEquals(p1,p3);
    }
    
    @Test
    void distanceTester() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(3, 4);
        assertEquals(5.0, p1.distance(p2), 0.0001);
    }
}
