package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.controller.Controller;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.application.*;
import java.io.IOException;
import java.util.Map;
import feup2526.ldts.t02g03.states.GameOverState;
import feup2526.ldts.t02g03.model.menu.GameOver;
import feup2526.ldts.t02g03.model.menu.User;

public class GameController extends Controller<Level> {
    private final Level level;
    private final Map<Class<?>, LaneController> controllerMap;
    private final PlayerController playerController;
    private final InputHandler inputHandler;
    private final ScoreManager scoreManager;
    private final LaneGenerationManager laneGenerationManager;
    private final PhysicsManager physicsManager;
    private final CollisionManager collisionManager;
    private final CameraManager cameraManager;

    public GameController(Level level, PlayerController playerController, InputHandler inputHandler,
            ScoreManager scoreManager, Map<Class<?>, LaneController> controllerMap,
            LaneGenerationManager laneGenerationManager, PhysicsManager physicsManager,
            CollisionManager collisionManager, CameraManager cameraManager) {
        super(level);
        this.level = level;
        this.controllerMap = controllerMap;
        this.playerController = playerController;
        this.inputHandler = inputHandler;
        this.scoreManager = scoreManager;
        this.laneGenerationManager = laneGenerationManager;
        this.physicsManager = physicsManager;
        this.collisionManager = collisionManager;
        this.cameraManager = cameraManager;
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
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

        User user = game.getCurrentUser();
        int score = scoreManager.getRunScore().getCount();
        int highScore = (user != null) ? user.getHighScore() : 0;
        int coins = (user != null) ? user.getCoins() : 0;

        game.setState(new GameOverState(new GameOver(score, highScore, coins)));
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

        cameraManager.update();
        laneGenerationManager.update();
        updateLanes();
        playerController.update();
        physicsManager.resolvePlatformPhysics();
        collisionManager.checkCollisions();
        scoreManager.updateScore();
    }

    private LaneController getController(Lane lane) {
        return controllerMap.get(lane.getClass());
    }

    public void updateLanes() {
        int cameraY = (int) level.getCamera().getY();
        int buffer = 6;
        int startRow = cameraY - buffer;
        int endRow = cameraY + level.getGrid().getH() + buffer;

        for (int row = startRow; row <= endRow; row++) {
            Lane lane = level.getLane(row);
            if (lane != null) {
                getController(lane).update(lane, level);
            }
        }
    }
}
