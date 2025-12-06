package feup2526.ldts.t02g03.controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameController extends Controller<Level> {
    private final Level level;
    private final List<RoadLaneController> laneControllers;
    private final List<RiverController> riverControllers;
    private final List<SafeLaneController> safeLaneControllers;
    private final PlayerController playerController;
    private final CoinCounter coinCounter;
    public final RunScore runScore;
    public final HighestScore highestScore;
    private int minRowReached;

    public GameController(Level level) {
        super(level);
        this.level = level;
        this.laneControllers = new ArrayList<>();
        this.riverControllers = new ArrayList<>();
        this.safeLaneControllers = new ArrayList<>();
        this.playerController = new PlayerController(level.getPlayer());
        this.coinCounter = new CoinCounter();
        this.runScore = new RunScore();
        this.highestScore = new HighestScore();
        this.minRowReached = (int) level.getPlayer().getPosition().getY();

        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                laneControllers.add(new RoadLaneController((RoadLane) lane, level.getGrid(), 0.01, 3, 2, 2));
            } else if (lane instanceof River) {
                riverControllers.add(new RiverController((River) lane, level.getGrid(), 0.05, 3, 2, 2));
            } else if (lane instanceof SafeLane) {
                safeLaneControllers.add(new SafeLaneController((SafeLane) lane, level.getGrid(), 0.3));
            }
        }
    }

    @Override
    public void step(feup2526.ldts.t02g03.Game game, KeyStroke key, long time) throws java.io.IOException {
        if (key != null) {
            if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'q' || key.getCharacter() == 'Q')) {
                game.setState(new feup2526.ldts.t02g03.states.MenuState(new feup2526.ldts.t02g03.model.menu.Menu()));
            } else if (key.getKeyType() == KeyType.Escape) {
                game.setState(new feup2526.ldts.t02g03.states.MenuState(new feup2526.ldts.t02g03.model.menu.Menu()));
            } else {
                handleInput(key);
            }
        }
        update();
        if (level.isGameOver()) {
            game.setState(new feup2526.ldts.t02g03.states.MenuState(new feup2526.ldts.t02g03.model.menu.Menu()));
        }
    }

    public void update() {
        if (level.isCollisionDetected()) {
            if (System.currentTimeMillis() - level.getCollisionTime() > 1000) {
                level.setGameOver(true);
            }
            return;
        }
        if (level.isGameOver())
            return;

        playerController.update();
        updateLanes();
        checkCollisions();
    }

    private void checkCollisions() {
        Player player = level.getPlayer();
        double pMin = player.getPosition().getX() + player.getOffsetX();
        double pMax = pMin + player.getWidth();
        int playerRow = (int) Math.round(player.getPosition().getY());

        for (Lane lane : level.getLanes()) {
            if (lane.getRow() == playerRow) {
                if (lane instanceof RoadLane) {
                    RoadLane roadLane = (RoadLane) lane;
                    for (Vehicle v : roadLane.getVehicles()) {
                        double vMin = v.getPosition().getX() + v.getOffsetX();
                        double vMax = vMin + v.getWidth();

                        if (pMin < vMax && pMax > vMin) {
                            level.handleCollision();
                            return;
                        }
                    }
                } else if (lane instanceof River) {
                    River river = (River) lane;
                    boolean onLog = false;
                    for (Log l : river.getLogs()) {
                        double lMin = l.getPosition().getX() + l.getOffsetX();
                        double lMax = lMin + l.getWidth();

                        if (pMin < lMax && pMax > lMin) {
                            onLog = true;
                            double dist = playerController.getDistanceToTarget();

                            if (dist < 0.1) {
                                player.setOnLog(true);
                            }

                            if (player.isOnLog()) {
                                if (Math.abs(player.getPosition().getY() - playerRow) < 0.1) {
                                    playerController.moveTo(new Position(l.getPosition().getX(), playerRow));
                                } else {
                                    playerController.moveTo(new Position(player.getPosition().getX(), playerRow));
                                }
                                movePlayerWithLog(player, l, river);
                            }
                            break;
                        }
                    }
                    if (!onLog) {
                        level.handleCollision();
                        return;
                    }
                } else if (lane instanceof SafeLane) {
                    SafeLane safeLane = (SafeLane) lane;
                    for (int i = 0; i < safeLane.getCoins().size(); i++) {
                        Coin c = safeLane.getCoins().get(i);
                        double cMin = c.getPosition().getX() + c.getOffsetX();
                        double cMax = cMin + c.getWidth();

                        if (pMin < cMax && pMax > cMin) {
                            safeLane.getCoins().remove(i);
                            i--;
                            coinCounter.increment();
                        }
                    }
                }
            }
        }
    }

    private void movePlayerWithLog(Player player, Log log, River river) {
        if (log.getDirection() == Direction.LEFT) {
            playerController.moveWithPlatform(-river.getSpeed());
        } else {
            playerController.moveWithPlatform(river.getSpeed());
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
            default:
                break;
        }

        if (dir != null) {
            if (dir == Direction.UP || dir == Direction.DOWN) {
                double currentY = level.getPlayer().getPosition().getY();
                double targetY = Math.round(currentY) + (dir == Direction.UP ? -1 : 1);
                Position newPos = new Position(level.getPlayer().getPosition().getX(), targetY);

                if (level.getGrid().isInside(newPos) && !isTree(newPos)) {
                    playerController.moveTo(newPos);
                    if (newPos.getY() < minRowReached) {
                        runScore.increment();
                        minRowReached = (int) newPos.getY();
                    }
                    return true;
                }
            } else {
                Position nextPos;
                if (dir == Direction.LEFT) {
                    nextPos = new Position(level.getPlayer().getTargetPosition().getX() - 0.8,
                            level.getPlayer().getTargetPosition().getY());
                } else {
                    nextPos = new Position(level.getPlayer().getTargetPosition().getX() + 0.8,
                            level.getPlayer().getTargetPosition().getY());
                }

                if (level.getGrid().isInside(nextPos) && !isTree(nextPos)) {
                    playerController.changeTargetPosition(dir, level.getGrid());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTree(Position p) {
        double pMin = p.getX() + level.getPlayer().getOffsetX();
        double pMax = pMin + level.getPlayer().getWidth();
        int row = (int) Math.round(p.getY());

        Lane lane = level.getLane(row);
        if (lane instanceof SafeLane) {
            return ((SafeLane) lane).getTrees().stream().anyMatch(tree -> {
                double tMin = tree.getPosition().getX();
                double tMax = tMin + 1.0;
                return pMin < tMax && pMax > tMin;
            });
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
        for (SafeLaneController controller : safeLaneControllers) {
            controller.step();
        }
    }
}
