package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.view.LanternaViewer;
import java.io.IOException;

public abstract class Viewer<T> {
    private final T model;

    public Viewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(LanternaViewer gui) throws IOException;
}
