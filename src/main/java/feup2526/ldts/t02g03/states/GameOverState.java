package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.GameOverController;
import feup2526.ldts.t02g03.model.menu.GameOver;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.GameOverViewer;

public class GameOverState extends State<GameOver> {
    public GameOverState(GameOver model) {
        super(model);
    }

    @Override
    protected Viewer<GameOver> createViewer() {
        return new GameOverViewer(getModel());
    }

    @Override
    protected Controller<GameOver> createController() {
        return new GameOverController(getModel());
    }
}
