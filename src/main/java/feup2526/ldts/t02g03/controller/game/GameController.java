package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.controller.Controller;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;

import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;

public class GameController extends Controller<Level> {
    private final Level level;
    private final RoadLaneController roadLaneController;
    private final RiverController riverController;
    private final SafeLaneController safeLaneController;
    private final PlayerController playerController;
    public final RunScore runScore;
    public final HighestScore highestScore;
    private int minRowReached;

    public GameController(Level level) {
        super(level);
        this.level = level;
        this.roadLaneController = new RoadLaneController(0.01, 3, 2, 2);
        this.riverController = new RiverController(0.05, 3, 2, 2);
        this.safeLaneController = new SafeLaneController(0.3);
        this.playerController = new PlayerController(level.getPlayer());
        this.runScore = level.getRunScore();
        this.highestScore = new HighestScore();
        this.minRowReached = (int) level.getPlayer().getPosition().getY();
    }

    @Override
    public void step(feup2526.ldts.t02g03.application.Game game, KeyStroke key, long time) throws java.io.IOException {
        if (key != null) {
            if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'q' || key.getCharacter() == 'Q')) {
                returnToMenu(game);
            } else if (key.getKeyType() == KeyType.Escape) {
                returnToMenu(game);
            } else {
                handleInput(key);
            }
        }
        update();
        if (level.isGameOver()) {
            returnToMenu(game);
        }
    }

    private void returnToMenu(feup2526.ldts.t02g03.application.Game game) throws java.io.IOException {
        updateUserStats(game);
        game.returnToMenu();
    }

    private void updateUserStats(feup2526.ldts.t02g03.application.Game game) {
        User user = game.getCurrentUser();
        if (user != null) {
            int coins = level.getCoinCounter().getCount();
            int score = level.getRunScore().getCount();

            user.setCoins(user.getCoins() + coins);
            if (score > user.getHighScore()) {
                user.setHighScore(score);
            }

            new UserManager().updateUser(user);
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
        updateLanes();
        playerController.update();
        resolvePlatformPhysics();
        checkCollisions();

        int currentRow = (int) Math.round(level.getPlayer().getPosition().getY());
        if (currentRow < minRowReached) {
            runScore.increment(minRowReached - currentRow);
            minRowReached = currentRow;
        }
    }

    // Test the players current and target position against logs
    private void resolvePlatformPhysics() {
        Player player = level.getPlayer();
        movePointWithLog(player.getPosition(), true);
        movePointWithLog(player.getTargetPosition(), false);
    }

    private void checkCollisions() {
        int playerRow = (int) Math.round(level.getPlayer().getPosition().getY());

        // Check current lane and adjacent lanes (just in case of overlap or movement)
        for (int row = playerRow - 1; row <= playerRow + 1; row++) {
            Lane lane = level.getLane(row);
            if (lane != null) {
                getController(lane).handleCollision(lane, level);
            }
        }
    }

    private LaneController getController(Lane lane) {
        if (lane instanceof RoadLane)
            return roadLaneController;
        if (lane instanceof River)
            return riverController;
        if (lane instanceof SafeLane)
            return safeLaneController;
        return null;
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

        // snapping player to center of a log
        if ((dir == Direction.UP || dir == Direction.DOWN) && destLane instanceof River) {
            Log targetLog = riverController.getLogAt((River) destLane, tp);
            if (targetLog != null) {
                double centeredX = targetLog.getPosition().getX();
                tp = new Position(centeredX, tp.getY());
            }
        }
        // snap player to grid cell center
        else if (destLane != null && !(destLane instanceof River)) {
            tp = new Position((double) Math.round(tp.getX()), (int) Math.round(tp.getY()));
        }

        if (!isBlocked(tp)) {
            playerController.moveTo(tp);
            return true;
        }

        return false;
    }

    /*
     * Somewhat confusing method,
     * getting coordinates, we get if there is a log on not
     * If there is a log, We check if theres a player there:
     * If yes, move actual player position.
     * If not, its because the input was the target destination of the player when
     * jump was made,
     * So update the target position
     */
    private void movePointWithLog(Position p, boolean isPlayerBody) {
        int row = (int) Math.round(p.getY());
        Lane lane = level.getLane(row);
        if (lane instanceof River) {
            River river = (River) lane;
            Log log = riverController.getLogAt(river, p);
            if (log != null) {
                double speed = river.getSpeed();
                if (river.getDirection() == Direction.LEFT) {
                    speed = -speed;
                }
                if (isPlayerBody) {
                    Position current = level.getPlayer().getPosition();
                    level.getPlayer().setPosition(new Position(current.getX() + speed, current.getY()));
                } else {
                    Position target = level.getPlayer().getTargetPosition();
                    level.getPlayer().setTargetPosition(new Position(target.getX() + speed, target.getY()));
                }
            }
            // no log, and player on its final position, so its dead
            else if (isPlayerBody) {
                if (level.getPlayer().getPosition().distance(level.getPlayer().getTargetPosition()) < 0.2) {
                    level.handleCollision();
                }
            }
        }
    }

    private boolean isBlocked(Position p) {
        int row = (int) Math.round(p.getY());
        Lane lane = level.getLane(row);
        if (lane != null) {
            return getController(lane).isBlocked(lane, p);
        }
        return false;
    }

    public void updateLanes() {
        for (Lane lane : level.getLanes()) {
            getController(lane).update(lane, level);
        }
    }
}
