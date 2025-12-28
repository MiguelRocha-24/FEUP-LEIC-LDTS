package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SafeLaneControllerTest {
    @Test
    void testCoinCollection() {
        SafeLane lane = new SafeLane(1, 10, false);
        Coin c = new Coin(new Position(5, 1)); // w=1.0. range 5.0-6.0
        lane.addCoin(c);
        
        Level level = new Level(10, 10);
        Player p = level.getPlayer();
        p.setPosition(new Position(5.2, 1)); // Player overlaps coin
        
        SafeLaneController slc = new SafeLaneController(0.0);
        slc.handleCollision(lane, level);
        
        assertTrue(lane.getCoins().isEmpty());
        assertEquals(1, level.getCoinCounter().getCount());
    }

    @Test
    void testIsBlockedByTree() {
        SafeLane lane = new SafeLane(1, 10, false);
        Tree t = new Tree(new Position(5, 1));
        lane.addTree(t);
        
        SafeLaneController slc = new SafeLaneController(0.0);
        
        // Check overlap. Tree 5.0-6.0.
        // Try pos 5.5. Range 5.5-6.5. Overlaps!
        assertTrue(slc.isBlocked(lane, new Position(5.5, 1)));
        
        // Try pos 7.0. Range 7.0-8.0. No overlap.
        assertFalse(slc.isBlocked(lane, new Position(7.0, 1)));
    }
}
