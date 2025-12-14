package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import java.util.Map;

public class PhysicsManager {
    private final Level level;
    private final Map<Class<?>, LaneController> controllerMap;

    public PhysicsManager(Level level, Map<Class<?>, LaneController> controllerMap) {
        this.level = level;
        this.controllerMap = controllerMap;
    }

    public void resolvePlatformPhysics() {
        Player player = level.getPlayer();
        movePointWithLog(player.getPosition(), true);
        movePointWithLog(player.getTargetPosition(), false);
    }

    private void movePointWithLog(Position p, boolean isPlayerBody) {
        int row = (int) Math.round(p.getY());
        Lane lane = level.getLane(row);
        LaneController controller = controllerMap.get(lane.getClass());
        controller.handlePhysics(lane, level, p, isPlayerBody);
    }
}
