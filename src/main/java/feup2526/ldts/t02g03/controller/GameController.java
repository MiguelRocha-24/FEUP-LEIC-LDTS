package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private final Level level;
    private final Scanner scanner;
    private final List<RoadLaneController> laneControllers;
    private final List<RiverController> riverControllers;

    public GameController(Level level){
        this.level = level;
        this.scanner = new Scanner(System.in);
        this.laneControllers = new ArrayList<>();
        this.riverControllers = new ArrayList<>();

        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                laneControllers.add(new RoadLaneController((RoadLane) lane, level.getGrid(), 0.3, 3, 2, 2));
            } else if (lane instanceof River) {
                riverControllers.add(new RiverController((River) lane, level.getGrid(), 0.3, 3, 2, 2));
            }
        }
    }

    public void update() {
        if (level.isGameOver())
            return;
        updatePlayer();
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

    public boolean updatePlayer() {
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim().toUpperCase();
            if (line.equals("Q") || line.equals("ESC")) {
                level.quit();
                return true;
            }
            if (!line.isEmpty()) {
                char c = line.charAt(0);
                Direction dir = null;
                switch (c) {
                    case 'W':
                        dir = Direction.UP;
                        break;
                    case 'S':
                        dir = Direction.DOWN;
                        break;
                    case 'A':
                        dir = Direction.LEFT;
                        break;
                    case 'D':
                        dir = Direction.RIGHT;
                        break;
                }
                if (dir != null) {
                    level.getPlayer().move(dir, level.getGrid());
                    return true;
                }
            }
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

    public Level getLevel(){return level;}
    public Scanner getScanner(){return scanner;}
    public List<RoadLaneController> getLaneControllers(){return laneControllers;}
}
