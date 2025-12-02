package feup2526.ldts.t02g03.controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Level level;
    private final List<RoadLaneController> laneControllers;
    private final List<RiverController> riverControllers;

    public GameController(Level level) {
        this.level = level;
        this.laneControllers = new ArrayList<>();
        this.riverControllers = new ArrayList<>();

        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                laneControllers.add(new RoadLaneController((RoadLane) lane, level.getGrid(), 0.01, 3, 2, 2));
            } else if (lane instanceof River) {
                riverControllers.add(new RiverController((River) lane, level.getGrid(), 0.05, 3, 2, 2));
            }
        }
    }

    public void update() {
        if (level.isGameOver())
            return;
        updateLanes();
        checkCollisions();
    }

    private void checkCollisions() {
        for (Lane lane : level.getLanes()) {
            if (lane.getRow() == level.getPlayer().getPosition().getY()) {
                if (lane instanceof RoadLane) {
                    RoadLane roadLane = (RoadLane) lane;
                    for (Vehicle v : roadLane.getVehicles()) {
                        if (Math.abs(v.getPosition().getX() - level.getPlayer().getPosition().getX()) < 0.8 &&
                                Math.abs(v.getPosition().getY() - level.getPlayer().getPosition().getY()) < 0.8) {
                            level.setGameOver(true);
                            return;
                        }
                    }
                } else if (lane instanceof River) {
                    River river = (River) lane;
                    boolean onLog = false;
                    for (Log l : river.getLogs()) {
                        if (Math.abs(l.getPosition().getX() - level.getPlayer().getPosition().getX()) < 0.8 &&
                                Math.abs(l.getPosition().getY() - level.getPlayer().getPosition().getY()) < 0.8) {
                            onLog = true;
                            break;
                        }
                    }
                    if (!onLog) {
                        level.setGameOver(true);
                        return;
                    }
                }
            }
        }
    }

    public boolean handleInput(KeyStroke key) {
        if (key == null)
            return false;
        if (key.getKeyType() == KeyType.EOF)
            return true; 
        if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'q' || key.getCharacter() == 'Q')) {
            level.quit();
            return true;
        }
        if (key.getKeyType() == KeyType.Escape) {
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
                if (key.getCharacter() == 'w' || key.getCharacter() == 'W')
                    dir = Direction.UP;
                if (key.getCharacter() == 's' || key.getCharacter() == 'S')
                    dir = Direction.DOWN;
                if (key.getCharacter() == 'a' || key.getCharacter() == 'A')
                    dir = Direction.LEFT;
                if (key.getCharacter() == 'd' || key.getCharacter() == 'D')
                    dir = Direction.RIGHT;
                break;
        }

        if (dir != null) {
            level.getPlayer().move(dir, level.getGrid());
            return true;
        }
        return false;
    }

    public void updateLanes() {
        for (RoadLaneController controller : laneControllers) {
            controller.step();
        }
        for (RiverController controller : riverControllers) {
            controller.step();
        }
    }
}
