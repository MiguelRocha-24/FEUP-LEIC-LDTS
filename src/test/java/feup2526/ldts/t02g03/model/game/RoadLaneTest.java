package feup2526.ldts.t02g03.model.game;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.RoadLane;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Vehicle;
import feup2526.ldts.t02g03.model.game.Position;


public class RoadLaneTest {
    private RoadLane a; 
    @BeforeEach
    void setUp(){
        a = new RoadLane(Direction.LEFT,1,1);
    }

    @Test
    void testBuilderAndGetter(){
        assertNotNull(a.getVehicles());
    }


    @Test
    void testAddVehicleTrivial(){
        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        assertEquals(1,a.getVehicles().size());
        assertEquals(new Vehicle(new Position(1,1),Direction.LEFT),a.getVehicles().get(0));
    }

    @Test
    void testAddVehicleArguments(){
        //Arguments
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {a.addVehicle(null);});
        assertTrue(thrown.getMessage().contains("required"));

        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {a.addVehicle(new Vehicle(new Position(1,2),Direction.LEFT));});
        assertTrue(thrown2.getMessage().contains("this"));

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {a.addVehicle(new Vehicle(new Position(1,1),Direction.RIGHT));});
        assertTrue(thrown3.getMessage().contains("direction"));
    }

    @Test
    void testAddVehicleOrdering(){
        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        a.addVehicle(new Vehicle(new Position(7,1),Direction.LEFT));
        a.addVehicle(new Vehicle(new Position(3,1),Direction.LEFT));
        a.addVehicle(new Vehicle(new Position(5,1),Direction.LEFT));

        assertEquals(4,a.getVehicles().size());
        assertEquals(new Vehicle(new Position(1,1),Direction.LEFT),a.getVehicles().get(0));
        assertEquals(new Vehicle(new Position(3,1),Direction.LEFT),a.getVehicles().get(1));
        assertEquals(new Vehicle(new Position(5,1),Direction.LEFT),a.getVehicles().get(2));
        assertEquals(new Vehicle(new Position(7,1),Direction.LEFT),a.getVehicles().get(3));
    }

    @Test
    void testRemoveVehicleFailure(){
        //arguments
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {a.removeVehicle(null);});
        assertTrue(thrown.getMessage().contains("required"));

        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {a.removeVehicle(new Vehicle(new Position(2,1),Direction.LEFT));});
        assertTrue(thrown2.getMessage().contains("not in lane"));
        
        a.removeVehicle(a.getVehicles().get(0));    
        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {a.removeVehicle(new Vehicle(new Position(2,1),Direction.LEFT));});
        assertTrue(thrown3.getMessage().contains("not in lane"));
    }

    @Test
    void testRemoveVehicleSuccess(){
        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        a.removeVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        assertEquals(0,a.getVehicles().size());

        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        a.removeVehicle(a.getVehicles().get(0));
        assertEquals(0,a.getVehicles().size());

        a.addVehicle(new Vehicle(new Position(1,1),Direction.LEFT));
        a.addVehicle(new Vehicle(new Position(7,1),Direction.LEFT));
        a.removeVehicle(a.getVehicles().get(1));
        assertEquals(1,a.getVehicles().size());
    }
}
