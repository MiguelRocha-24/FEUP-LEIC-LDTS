package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.Vehicle;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.game.Direction;

import static org.junit.jupiter.api.Assertions.*;
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
        v.move(1);
        assertEquals(new Position(2,1), v.getPosition());
        v.setDirection(Direction.LEFT);
        v.move(1);
        assertEquals(new Position(1,1), v.getPosition());
        v.setDirection(Direction.UP);
        v.move(1);
        assertEquals(new Position(1,0), v.getPosition());
        v.setDirection(Direction.DOWN);
        v.move(1);
        assertEquals(new Position(1,1), v.getPosition());
    }

    @Test
    void moveUncheckedTest(){
        Vehicle v = new Vehicle(new Position(1,1), Direction.RIGHT);
        v.move(3);
        assertEquals(new Position(4,1), v.getPosition());

        v.move(0);
        assertEquals(new Position(4,1), v.getPosition());
    }

    @Test
    void equalsTest(){
        Vehicle v = new Vehicle(new Position(1,1), Direction.RIGHT);
        Vehicle v2 = new Vehicle(new Position(1,1), Direction.RIGHT);
        assertEquals(v,v2);

        assertNotEquals(v,new Vehicle(new Position(1,1), Direction.LEFT));
        assertNotEquals(v,null);
        assertNotEquals(v,new Object());
    }
}
