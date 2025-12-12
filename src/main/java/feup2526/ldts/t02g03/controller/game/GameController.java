package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.controller.Controller;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.application.*;
import java.io.IOException;
import java.util.Map;

public class GameController extends Controller<Level> {
    private final Level level;
    private final Map<Class<?>, LaneController> controllerMap;
    private final PlayerController playerController;
    private final InputHandler inputHandler;
    private final ScoreManager scoreManager;

    public GameController(Level level, PlayerController playerController, InputHandler inputHandler,
            ScoreManager scoreManager, Map<Class<?>, LaneController> controllerMap) {
        super(level);
        this.level = level;
        this.controllerMap = controllerMap;
        this.playerController = playerController;
        this.inputHandler = inputHandler;
        this.scoreManager = scoreManager;
    }

    @Override
    public void step(Game game, KeyStroke key, long time) throws IOException {
        if (key != null) {
            if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'q' || key.getCharacter() == 'Q')) {
                returnToMenu(game);
            } else if (key.getKeyType() == KeyType.Escape) {
                returnToMenu(game);
            } else {
                inputHandler.handleInput(key);
            }
        }
        update();
        if (level.isGameOver()) {
            returnToMenu(game);
        }
    }

    private void returnToMenu(Game game) throws IOException {
        scoreManager.updateUserStats(game);
        game.returnToMenu();
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
        scoreManager.updateScore();
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
        return controllerMap.get(lane.getClass());
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
        LaneController controller = getController(lane);
        controller.handlePhysics(lane, level, p, isPlayerBody);
    }

    public void updateLanes() {
        for (Lane lane : level.getLanes()) {
            getController(lane).update(lane, level);
        }
    }
}
