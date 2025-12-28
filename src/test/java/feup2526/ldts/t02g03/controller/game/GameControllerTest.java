package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.states.GameOverState;
import feup2526.ldts.t02g03.model.game.Camera;
import feup2526.ldts.t02g03.model.game.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.RunScore;

class GameControllerTest {
    private GameController gameController;
    private Level level;
    private Game game;
    private Map<Class<?>, LaneController> controllerMap;
    private ScoreManager scoreManager;
    private LaneGenerationManager laneGenManager;
    private PhysicsManager physicsManager;
    private CollisionManager collisionManager;
    private CameraManager cameraManager;
    private PlayerController playerController;
    private InputHandler inputHandler;

    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        game = mock(Game.class);
        controllerMap = new HashMap<>();
        scoreManager = mock(ScoreManager.class);
        laneGenManager = mock(LaneGenerationManager.class);
        physicsManager = mock(PhysicsManager.class);
        collisionManager = mock(CollisionManager.class);
        cameraManager = mock(CameraManager.class);
        playerController = mock(PlayerController.class);
        inputHandler = mock(InputHandler.class);
        Camera mockCamera = mock(Camera.class);
        when(level.getCamera()).thenReturn(mockCamera);
        when(mockCamera.getY()).thenReturn(10.0);
        when(level.getGrid()).thenReturn(new Grid(10, 20));
        gameController = new GameController(level, playerController, inputHandler,
                scoreManager, controllerMap, laneGenManager, physicsManager,
                collisionManager, cameraManager);
    }

    @Test
    void testUpdateCallsSubManagers() {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        gameController.update();

        verify(cameraManager).update();
        verify(laneGenManager).update();
        verify(playerController).update();
        verify(physicsManager).resolvePlatformPhysics();
        verify(collisionManager).checkCollisions();
        verify(scoreManager).updateScore();
    }

    @Test
    void testGameOverTransition() throws IOException {
        when(level.isGameOver()).thenReturn(true);
        when(scoreManager.getRunScore()).thenReturn(mock(RunScore.class));
        gameController.step(game, null, 100);
        verify(game).setState(any(GameOverState.class));
    }

    @Test
    void testStepQKey() throws IOException {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        when(scoreManager.getRunScore()).thenReturn(mock(RunScore.class));
        KeyStroke qKey = new KeyStroke('q', false, false);
        gameController.step(game, qKey, 100);
        verify(game).setState(any(GameOverState.class));
    }

    @Test
    void testStepEscKey() throws IOException {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        when(scoreManager.getRunScore()).thenReturn(mock(RunScore.class));
        KeyStroke escKey = new KeyStroke(KeyType.Escape);
        gameController.step(game, escKey, 100);
        verify(game).setState(any(GameOverState.class));
    }

    @Test
    void testStepNormalKey() throws IOException {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        KeyStroke wKey = new KeyStroke('w', false, false);
        gameController.step(game, wKey, 100);
        verify(inputHandler).handleInput(wKey);
    }

    @Test
    void testUpdateCollisionTimeout() {
        when(level.isCollisionDetected()).thenReturn(true);
        when(level.getCollisionTime()).thenReturn(System.currentTimeMillis() - 1500);
        gameController.update();
        verify(level).setGameOver(true);
    }

    @Test
    void testUpdateLanes() {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        feup2526.ldts.t02g03.model.game.RoadLane mockLane = mock(feup2526.ldts.t02g03.model.game.RoadLane.class);
        when(level.getLane(anyInt())).thenReturn(mockLane);
        LaneController mockLaneController = mock(LaneController.class);
        controllerMap.put(feup2526.ldts.t02g03.model.game.RoadLane.class, mockLaneController);
        gameController.update();
        verify(mockLaneController, atLeastOnce()).update(eq(mockLane), eq(level));
    }
}
