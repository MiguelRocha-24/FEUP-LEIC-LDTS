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
    private int minGeneratedRow;
    private int maxGeneratedRow;

    public GameController(Level level, PlayerController playerController, InputHandler inputHandler,
            ScoreManager scoreManager, Map<Class<?>, LaneController> controllerMap) {
        super(level);
        this.level = level;
        this.controllerMap = controllerMap;
        this.playerController = playerController;
        this.inputHandler = inputHandler;
        this.scoreManager = scoreManager;

        this.maxGeneratedRow = level.getGrid().getH() - 1;
        this.minGeneratedRow = level.getGrid().getH() - 2;
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

        // Update Camera
        Camera camera = level.getCamera();
        Player player = level.getPlayer();

        // Start camera on first movement
        if (!camera.isMoving() && player.getPosition().getY() < level.getGrid().getH() - 2) {
            camera.startMoving();
        }

        camera.update(player.getPosition().getY(), level.getGrid().getH());

        // Check death condition (player below screen)
        if (player.getPosition().getY() > camera.getY() + level.getGrid().getH()) {
            level.setGameOver(true);
        }

        // Generate new lanes
        // Generate new lanes
        updateLanesGeneration();

        updateLanes();
        playerController.update();
        resolvePlatformPhysics();
        checkCollisions();
        scoreManager.updateScore();
    }

    private void updateLanesGeneration() {
        Camera camera = level.getCamera();
        int cameraTopRow = (int) camera.getY();
        int generationBuffer = 6;
        int targetMinRow = cameraTopRow - generationBuffer;


        while (minGeneratedRow > targetMinRow) {
            minGeneratedRow--;
            generateLane(minGeneratedRow);
        }

        int cameraBottomRow = (int) (camera.getY() + level.getGrid().getH());
        int cleanupThreshold = cameraBottomRow + 2;

        while (maxGeneratedRow > cleanupThreshold) {
            level.removeLane(maxGeneratedRow);
            maxGeneratedRow--;
        }
    }

    private void generateLane(int row) {
        if (level.getLane(row) != null)
            return;

        Direction dir = (Math.abs(row) % 2 == 0) ? Direction.RIGHT : Direction.LEFT;
        //aumenta dificuldade -> para ser ajustado
        double speed = 0.05 + (Math.abs(row) * 0.0001);
        double choseLane = Math.random();

        Lane lane;
        if (choseLane < 0.33) {
            lane = new RoadLane(dir, speed, row);
        } else if (choseLane < 0.66) {
            lane = new River(row, dir, speed);
        } else {
            lane = new SafeLane(row, level.getGrid().getW(), true);
        }
        level.addLane(row, lane);
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
