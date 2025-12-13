package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import java.util.Map;

public class CollisionManager {
    private final Level level;
    private final Map<Class<?>, LaneController> controllerMap;

    public CollisionManager(Level level, Map<Class<?>, LaneController> controllerMap) {
        this.level = level;
        this.controllerMap = controllerMap;
    }

    public void checkCollisions() {
        int playerRow = (int) level.getPlayer().getPosition().getY();

        for (int row = playerRow - 1; row <= playerRow + 1; row++) {
            Lane lane = level.getLane(row);
            if (lane != null) {
                LaneController controller = controllerMap.get(lane.getClass());
                controller.handleCollision(lane, level);
            }
        }
    }
}
