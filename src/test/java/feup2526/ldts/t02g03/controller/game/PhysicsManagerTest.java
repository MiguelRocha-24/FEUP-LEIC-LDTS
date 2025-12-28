package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;
import java.util.HashMap;

class PhysicsManagerTest {
    @Test
    void testDelegation() {
        Level level = new Level(10, 10);
        Lane lane8 = level.getLane(8);
        
        LaneController mockController = Mockito.mock(LaneController.class);
        Map<Class<?>, LaneController> map = new HashMap<>();
        map.put(SafeLane.class, mockController);
        
        PhysicsManager pm = new PhysicsManager(level, map);
        pm.resolvePlatformPhysics();
        
        // Physics checks player pos and target pos
        Mockito.verify(mockController, Mockito.atLeast(2)).handlePhysics(Mockito.eq(lane8), Mockito.eq(level), Mockito.any(), Mockito.anyBoolean());
    }
}
