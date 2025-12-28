package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.Position;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseLaneControllerTest {
    private static class ConcreteLaneController extends BaseLaneController {
        public ConcreteLaneController(double spawnChance) {
            super(spawnChance);
        }

        @Override
        public void update(Lane lane, Level level) {}

        @Override
        public void handleCollision(Lane lane, Level level) {}

        @Override
        public boolean isBlocked(Lane lane, Position pos) { return false; }
        
        // Expose rng or seed if needed, but we test getSnapPosition logic.
    }

    @Test
    void testGetSnapPosition() {
        BaseLaneController controller = new ConcreteLaneController(0.5);
        Lane lane = Mockito.mock(Lane.class);

        Position target = new Position(5.6, 3.2);
        Position snapped = controller.getSnapPosition(lane, target);

        // x snapped to Math.round(5.6) = 6.0
        // y snapped to Math.round(3.2) = 3 -> cast to int = 3
        assertEquals(6.0, snapped.getX(), 0.001);
        assertEquals(3.0, snapped.getY(), 0.001);
    }
}
