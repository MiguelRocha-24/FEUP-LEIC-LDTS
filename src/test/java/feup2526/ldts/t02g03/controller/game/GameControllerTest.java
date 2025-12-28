package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.states.GameOverState;
import feup2526.ldts.t02g03.model.game.Camera;
import feup2526.ldts.t02g03.model.game.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        gameController = new GameController(level, playerController, mock(InputHandler.class),
                scoreManager, controllerMap, laneGenManager, physicsManager,
                collisionManager, cameraManager);
    }

    @Test
    void testUpdateCallsSubManagers() {
        when(level.isGameOver()).thenReturn(false);
        when(level.isCollisionDetected()).thenReturn(false);
        
        // Mock Camera/Grid for updateLanes
        Camera mockCamera = mock(Camera.class);
        when(level.getCamera()).thenReturn(mockCamera);
        when(mockCamera.getY()).thenReturn(10.0);
        when(level.getGrid()).thenReturn(new Grid(10, 20));

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
        when(scoreManager.getRunScore()).thenReturn(mock(feup2526.ldts.t02g03.model.game.RunScore.class));

        gameController.step(game, null, 100);

        verify(game).setState(any(GameOverState.class));
    }
}
