package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.GUI;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public abstract class State<T> {
    private final T model;
    private Controller<T> controller;
    private Viewer<T> viewer;

    public State(T model) {
        this.model = model;
    }

    protected abstract Viewer<T> createViewer();

    protected abstract Controller<T> createController();

    public T getModel() {
        return model;
    }

    public void step(Game game, GUI gui, long time)
            throws IOException {
        if (controller == null) {
            controller = createController();
        }
        if (viewer == null) {
            viewer = createViewer();
        }
        KeyStroke key = gui.readInput();
        controller.step(game, key);
        viewer.draw(gui);
    }
}
