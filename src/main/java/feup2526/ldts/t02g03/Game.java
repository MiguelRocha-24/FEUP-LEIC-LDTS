package feup2526.ldts.t02g03;

import feup2526.ldts.t02g03.states.State;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.states.GameState;
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

    public Game() throws IOException, FontFormatException, URISyntaxException {
        this.starter = new LanternaStarter();
        this.gui = starter.createMenuViewer();
        this.state = new MenuState(new Menu());
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game().start();
    }

    public void setState(State<?> state) throws IOException {
        // Close old GUI before creating new one
        if (this.state != null && state != null && this.state.getClass() != state.getClass()) {
            gui.close();
        }

        if (state instanceof GameState && !(this.state instanceof GameState)) {
            this.gui = starter.createGameViewer();
        } else if (state instanceof MenuState && !(this.state instanceof MenuState)) {
            this.gui = starter.createMenuViewer();
        }

        this.state = state;
    }

    public int getTerminalGridWidth() {
        return starter.getGridWidth(gui);
    }

    public int getTerminalGridHeight() {
        return starter.getGridHeight(gui);
    }

    public void startGameState() throws IOException {
        if (this.state != null) {
            gui.close();
        }
        this.gui = starter.createGameViewer();
        this.state = new GameState(this);
    }

    public void start() throws IOException {
        int FPS = 30;
        int frameTime = 1000 / FPS;

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                gui.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
