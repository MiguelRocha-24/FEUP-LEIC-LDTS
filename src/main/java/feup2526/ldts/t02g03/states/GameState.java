package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.game.GameController;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.game.GameViewer;
import feup2526.ldts.t02g03.controller.game.*;
import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.model.menu.UserManager;
import java.util.*;

public class GameState extends State<Level> {
        private GameController controller;
        private GameViewer viewer;
        private final Game game;

        public GameState(Game game) {
                super(new Level(game.getTerminalGridWidth() + 1, game.getTerminalGridHeight()));
                this.game = game;

        }

        private Map<Class<?>, LaneController> createControllerMap() {
                Map<Class<?>, LaneController> controllerMap = new HashMap<>();
                controllerMap.put(RoadLane.class, new RoadLaneController(0.01, 2));
                controllerMap.put(River.class, new RiverController(0.05));
                controllerMap.put(SafeLane.class, new SafeLaneController(0.3));
                return controllerMap;
        }

        @Override
        protected Viewer<Level> createViewer() {
                viewer = new GameViewer(getModel());
                if (game.getCurrentUser() != null) {
                        viewer.setPlayerSkin(game.getCurrentUser().getEquippedSkin());
                }
                return viewer;
        }

        @Override
        protected Controller<Level> createController() {
                if (controller == null) {
                        Map<Class<?>, LaneController> controllerMap = createControllerMap();

                        PlayerController playerController = new PlayerController(getModel().getPlayer());
                        InputHandler inputHandler = new InputHandler(getModel(), playerController, controllerMap);
                        UserManager userManager = new UserManager();

                        ScoreManager scoreManager = new ScoreManager(getModel(), userManager);
                        LaneGenerationManager laneGenerationManager = new LaneGenerationManager(getModel());
                        PhysicsManager physicsManager = new PhysicsManager(getModel(), controllerMap);
                        CollisionManager collisionManager = new CollisionManager(getModel(), controllerMap);
                        CameraManager cameraManager = new CameraManager(getModel());

                        controller = new GameController(getModel(), playerController, inputHandler, scoreManager,
                                        controllerMap, laneGenerationManager, physicsManager, collisionManager,
                                        cameraManager);
                        // Pre-generate lanes
                        for (int i = 0; i < 300; i++) {
                                controller.update();
                        }
                }
                return controller;
        }
}
