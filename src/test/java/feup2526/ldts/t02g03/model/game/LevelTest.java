package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import feup2526.ldts.t02g03.model.game.*;

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

        // Assuming default start position is width/2, height-2 as per Level.java
        assertEquals(new Position(width / 2, height - 2), level.getPlayer().getPosition());

        List<Lane> lanes = level.getLanes();
        // Initial lanes: 2 safe lanes
        assertFalse(lanes.isEmpty());
    }

    @Test
    void testQuit(){
        assertFalse(level.isGameOver());
        level.quit();
        assertTrue(level.isGameOver());
    }

    @Test
    void testGameOverManual(){
        assertFalse(level.isGameOver());
        level.setGameOver(true);
        assertTrue(level.isGameOver());
    }

    @Test
    void testCollisionHandling(){
        assertFalse(level.isCollisionDetected());
        level.handleCollision();
        assertTrue(level.isCollisionDetected());
        assertTrue(level.getCollisionTime() > 0);
    }
}
