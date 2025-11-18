package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class VehicleTest {

    @Test
    void previewNextTest() {
        Vehicle v = new Vehicle(new Position(1,1), Direction.RIGHT);
        assertEquals(new Position(2,1), v.previewNext());
        v.previewNext();
        assertEquals(new Position(1,1), v.getPosition());
    }

    @Test
    void moveOneUncheckedTest(){
        Vehicle v = new Vehicle(new Position(1,1), Direction.RIGHT);
        v.moveOneUnchecked();
        assertEquals(new Position(2,1), v.getPosition());
        v.setDirection(Direction.LEFT);
        v.moveOneUnchecked();
        assertEquals(new Position(1,1), v.getPosition());
        v.setDirection(Direction.UP);
        v.moveOneUnchecked();
        assertEquals(new Position(1,0), v.getPosition());
        v.setDirection(Direction.DOWN);
        v.moveOneUnchecked();
        assertEquals(new Position(1,1), v.getPosition());
    }

    @Test
    void moveUncheckedTest(){
        Vehicle v = new Vehicle(new Position(1,1), Direction.RIGHT);
        v.moveUnchecked(3);
        assertEquals(new Position(4,1), v.getPosition());
        try{
            v.moveUnchecked(-2);
            fail("Expected IllegalArgumentException when distance is negative");
        }
        catch (IllegalArgumentException e){
        }
        v.moveUnchecked(0);
        assertEquals(new Position(4,1), v.getPosition());
    }
}