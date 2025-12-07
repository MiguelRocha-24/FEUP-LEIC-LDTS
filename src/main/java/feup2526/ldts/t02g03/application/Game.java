package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.states.State;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.states.GameState;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.LanternaViewer;
import feup2526.ldts.t02g03.view.LanternaStarter;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

import feup2526.ldts.t02g03.states.NewUserState;

public class Game {
    private final LanternaStarter starter;
    private LanternaViewer gui;
    private State<?> state;
    private feup2526.ldts.t02g03.model.menu.User currentUser;

    private final MenuState menuState;

    public Game() throws IOException, FontFormatException, URISyntaxException {
        this.starter = new LanternaStarter();
        this.gui = starter.createMenuViewer();
        this.menuState = new MenuState(new Menu());
        this.state = this.menuState;
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game().start();
    }

    public void setState(State<?> state) throws IOException {
        if (state == null) {
            this.state = null;
            return;
        }

        boolean currentIsGame = (this.state instanceof GameState);
        boolean nextIsGame = (state instanceof GameState);

        boolean currentIsMenu = (this.state instanceof MenuState || this.state instanceof NewUserState);
        boolean nextIsMenu = (state instanceof MenuState || state instanceof NewUserState);

        if (this.state != null) {
            if (currentIsGame && !nextIsGame) {
                gui.close();
            } else if (currentIsMenu && !nextIsMenu) {
                gui.close();
            }
        }

        if (nextIsGame && !currentIsGame) {
            this.gui = starter.createGameViewer();
        } else if (nextIsMenu && !currentIsMenu) {
            this.gui = starter.createMenuViewer();
        }

        this.state = state;
    }

    public feup2526.ldts.t02g03.model.menu.User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(feup2526.ldts.t02g03.model.menu.User currentUser) {
        this.currentUser = currentUser;
    }

    public int getTerminalGridWidth() {
        return starter.getGridWidth(gui);
    }

    public int getTerminalGridHeight() {
        return starter.getGridHeight(gui);
    }

    public void returnToMenu() throws IOException {
        setState(menuState);
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
