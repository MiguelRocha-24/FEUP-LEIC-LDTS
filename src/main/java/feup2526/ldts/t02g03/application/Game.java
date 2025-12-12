package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.states.State;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.states.GameState;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIFactory;
import feup2526.ldts.t02g03.view.LanternaGUIFactory;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.states.NewUserState;

public class Game {
    private final GUIFactory starter;
    private GUI gui;
    private State<?> state;
    private User currentUser;

    private final MenuState menuState;

    public Game(LanternaGUIFactory guiFactory) throws IOException, FontFormatException, URISyntaxException {
        this.starter = guiFactory;
        this.gui = guiFactory.createMenuGUI();
        this.menuState = new MenuState(new Menu());
        this.state = this.menuState;
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game(new LanternaGUIFactory()).start();
    }

    public void setState(State<?> state) {
        if (state == null) {
            this.state = null;
            return;
        }

        boolean currentIsGame = (this.state instanceof GameState);
        boolean nextIsGame = (state instanceof GameState);

        boolean currentIsMenu = (this.state instanceof MenuState || this.state instanceof NewUserState);
        boolean nextIsMenu = (state instanceof MenuState || state instanceof NewUserState);

        try {
            if (this.state != null) {
                if (currentIsGame && !nextIsGame) {
                    gui.close();
                } else if (currentIsMenu && !nextIsMenu) {
                    gui.close();
                }
            }

            if (nextIsGame && !currentIsGame) {
                this.gui = starter.createGameGUI();
            } else if (nextIsMenu && !currentIsMenu) {
                this.gui = starter.createMenuGUI();
            }
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
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

    public void returnToMenu() {
        setState(menuState);
    }

    public void startGameState() {
        if (this.state != null) {
            try {
                gui.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.gui = starter.createGameGUI();
        } catch (IOException | FontFormatException | URISyntaxException e) {
            e.printStackTrace();
        }
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
