package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private final Level level;
    private final Scanner scanner;
    private final List<RoadLaneController> laneControllers;

    public GameController(Level level) {
        this.level = level;
        this.scanner = new Scanner(System.in);
        this.laneControllers = new ArrayList<>();

        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                laneControllers.add(new RoadLaneController((RoadLane) lane, level.getGrid(), 0.3, 3, 2, 2));
            }
        }
    }

    public void update() {
        if (level.isGameOver())
            return;
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim().toUpperCase();
            Direction direction = null;
            if (!line.isEmpty()) {
                char c = line.charAt(0);
                switch (c) {
                    case 'W':
                        direction = Direction.UP;
                        break;
                    case 'S':
                        direction = Direction.DOWN;
                        break;
                    case 'A':
                        direction = Direction.LEFT;
                        break;
                    case 'D':
                        direction = Direction.RIGHT;
                        break;
                    default:
                        direction = null;
                        break;
                }
            }
            if (direction != null) {
                level.getPlayer().tryMove(direction, level.getGrid());
            }
        }
        for (RoadLaneController controller : laneControllers) {
            controller.step();
        }
    }
}
