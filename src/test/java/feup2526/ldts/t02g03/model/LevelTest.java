package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LevelTest{
    private Level level;
    private final int width = 10;
    private final int height = 10;

    @BeforeEach
    void setUp(){
        level = new Level(width, height);
    }

    @Test
    void testBuilderAndInitialize(){
        assertNotNull(level.getGrid());
        assertNotNull(level.getPlayer());
        assertNotNull(level.getLanes());

        assertEquals(width, level.getGrid().getW());
        assertEquals(height, level.getGrid().getH());

        assertEquals(new Position(width / 2, height - 1), level.getPlayer().getPosition());

        List<Lane> lanes = level.getLanes();
        assertEquals(height - 2, lanes.size());
    }

    @Test
    void testQuit(){
        assertFalse(level.isGameOver());
        level.quit();
        assertTrue(level.isGameOver());
    }

    @Test
    void testIsGameOver1(){
        Player player = level.getPlayer();
        player.tryMove(Direction.UP, level.getGrid());
        assertEquals(new Position(width / 2, height - 2), player.getPosition());

        RoadLane currentLane = null;
        for (Lane lane : level.getLanes()) {
            if (lane.getRow() == player.getPosition().getY() && lane instanceof RoadLane) {
                currentLane = (RoadLane) lane;
                break;
            }
        }

        Vehicle v = new Car(player.getPosition(), currentLane.getDirection());
        currentLane.addVehicle(v);
        assertTrue(level.isGameOver());
    }

    @Test
    void testIsGameOver2() {
        Player player = level.getPlayer();
        player.tryMove(Direction.UP, level.getGrid());
        RoadLane currentLane = null;
        for (Lane lane : level.getLanes()) {
            if (lane.getRow() == player.getPosition().getY() && lane instanceof RoadLane) {
                currentLane = (RoadLane) lane;
                break;
            }
        }
        boolean clear = true;
        for (Vehicle vehicle : currentLane.getVehicles()) {
            if(player.getPosition() == vehicle.getPosition()){
                clear = false;
            }
        }
        if (clear) assertFalse(level.isGameOver());
    }
}
