package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.LanternaViewer;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;

    public State(T model) {
        this.model = model;
        this.viewer = getViewer();
        this.controller = getController();
    }

    protected abstract Viewer<T> getViewer();

    protected abstract Controller<T> getController();

    public T getModel() {
        return model;
    }

    public void step(Game game, LanternaViewer gui, long time) throws IOException {
        KeyStroke key = gui.readInput();
        controller.step(game, key, time);
        viewer.draw(gui);
    }
}
