package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.GameController;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.game.GameViewer;

public class GameState extends State<Level> {
    private GameController controller;

    public GameState(Level model) {
        super(model);
        for (int i = 0; i < 300; i++) {
            controller.update();
        }
    }

    @Override
    protected Viewer<Level> getViewer() {
        return new GameViewer(getModel());
    }

    @Override
    protected Controller<Level> getController() {
        if (controller == null) {
            controller = new GameController(getModel());
        }
        return controller;
    }
}
