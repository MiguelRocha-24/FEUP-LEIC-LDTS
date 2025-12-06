package feup2526.ldts.t02g03;

import feup2526.ldts.t02g03.states.State;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.LanternaViewer;
import feup2526.ldts.t02g03.view.LanternaStarter;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final LanternaStarter starter;
    private LanternaViewer gui;
    private State<?> state;
    private int gridWidth;
    private int gridHeight;

    public Game() throws IOException, FontFormatException, URISyntaxException {
        this.starter = new LanternaStarter();
        this.gui = starter.createFullscreenViewer();
        this.gridWidth = starter.getGridWidth(gui);
        this.gridHeight = starter.getGridHeight(gui);
        this.state = new MenuState(new Menu());
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game().start();
    }

    public void setState(State<?> state) {
        this.state = state;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void start() throws IOException {
        int FPS = 30;
        int frameTime = 1000 / FPS;

        while (this.state != null) {
            long startTime = System.currentTimeMillis();

            state.step(this, gui, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
        gui.close();
    }
}
