package feup2526.ldts.t02g03.controller.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;

import java.util.Map;

public class InputHandler {
    private final Level level;
    private final PlayerController playerController;
    private final Map<Class<?>, LaneController> controllerMap;

    public InputHandler(Level level, PlayerController playerController, Map<Class<?>, LaneController> controllerMap) {
        this.level = level;
        this.playerController = playerController;
        this.controllerMap = controllerMap;
    }

    public boolean handleInput(KeyStroke key) {
        if (key == null)
            return false;
        if (key.getKeyType() == KeyType.EOF)
            return true;
        if ((key.getKeyType() == KeyType.Character && (key.getCharacter() == 'q' || key.getCharacter() == 'Q'))
                || key.getKeyType() == KeyType.Escape) {
            level.quit();
            return true;
        }

        Direction dir = null;
        switch (key.getKeyType()) {
            case ArrowUp:
                dir = Direction.UP;
                break;
            case ArrowDown:
                dir = Direction.DOWN;
                break;
            case ArrowLeft:
                dir = Direction.LEFT;
                break;
            case ArrowRight:
                dir = Direction.RIGHT;
                break;
            case Character:
                char c = Character.toLowerCase(key.getCharacter());
                if (c == 'w')
                    dir = Direction.UP;
                if (c == 's')
                    dir = Direction.DOWN;
                if (c == 'a')
                    dir = Direction.LEFT;
                if (c == 'd')
                    dir = Direction.RIGHT;
                break;
            default:
                break;
        }
        if (dir == null)
            return false;
        Position currentPos = level.getPlayer().getPosition();
        Position tp = null;
        // Calculate the destination (grid blocks)
        if (dir == Direction.UP)
            tp = new Position(currentPos.getX(), currentPos.getY() - 1);
        if (dir == Direction.DOWN)
            tp = new Position(currentPos.getX(), currentPos.getY() + 1);
        if (dir == Direction.LEFT)
            tp = new Position(currentPos.getX() - 1, currentPos.getY());
        if (dir == Direction.RIGHT)
            tp = new Position(currentPos.getX() + 1, currentPos.getY());
        if (tp == null)
            return false;

        int destRow = (int) Math.round(tp.getY());
        Lane destLane = level.getLane(destRow);

        // snapping player to center of a log or grid cell
        LaneController controller = getController(destLane);
        if (controller != null) {
            tp = controller.getSnapPosition(destLane, tp);
        } else {
            // Fallback if no controller (shouldn't happen)
            tp = new Position((double) Math.round(tp.getX()), (int) Math.round(tp.getY()));
        }

        if (!isBlocked(tp)) {
            playerController.moveTo(tp);
            return true;
        }

        return false;
    }

    private LaneController getController(Lane lane) {
        if (lane == null)
            return null;
        return controllerMap.get(lane.getClass());
    }

    private boolean isBlocked(Position p) {
        int row = (int) Math.round(p.getY());
        Lane lane = level.getLane(row);
        if (lane != null) {
            LaneController controller = getController(lane);
            if (controller != null) {
                return controller.isBlocked(lane, p);
            }
        }
        return false;
    }
}
