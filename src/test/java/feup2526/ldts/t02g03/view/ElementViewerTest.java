package feup2526.ldts.t02g03.view;
import feup2526.ldts.t02g03.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElementViewerTest{
    @Test
    void testPlayerViewer(){
        PlayerViewer viewer = new PlayerViewer();
        Player p = new Player(new Position(0, 0));
        assertEquals('P', viewer.getSymbol(p));
    }

    @Test
    void testCarViewer(){
        CarViewer viewer = new CarViewer();
        Car c = new Car(new Position(0, 0), Direction.RIGHT);
        assertEquals('C', viewer.getSymbol(c));
    }

    @Test
    void testBusViewer(){
        BusViewer viewer = new BusViewer();
        Bus b = new Bus(new Position(0, 0), Direction.RIGHT);
        assertEquals('B', viewer.getSymbol(b));
    }
}
