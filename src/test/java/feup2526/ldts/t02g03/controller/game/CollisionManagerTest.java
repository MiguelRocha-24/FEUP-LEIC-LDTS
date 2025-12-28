package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;
import java.util.HashMap;

class CollisionManagerTest {
    @Test
    void testDelegation() {
        Level level = new Level(10, 10);
        // Player at (5, 8). Rows to check: 7, 8, 9.
        Lane lane8 = level.getLane(8); 
        // Default level has SafeLane at 8 and 9.
        
        LaneController mockController = Mockito.mock(LaneController.class);
        Map<Class<?>, LaneController> map = new HashMap<>();
        map.put(SafeLane.class, mockController);
        
        CollisionManager cm = new CollisionManager(level, map);
        cm.checkCollisions();
        
        // Should handle collision for lane 8
        Mockito.verify(mockController, Mockito.atLeastOnce()).handleCollision(Mockito.eq(lane8), Mockito.eq(level));
    }
}
