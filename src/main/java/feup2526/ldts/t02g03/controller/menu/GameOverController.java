package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.GameOver;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class GameOverController extends Controller<GameOver> {
    public GameOverController(GameOver model) {
        super(model);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null)
            return;

        if (key.getKeyType() == KeyType.ArrowUp) {
            getModel().previousEntry();
        }

        if (key.getKeyType() == KeyType.ArrowDown) {
            getModel().nextEntry();
        }

        if (key.getKeyType() == KeyType.Enter) {
            if (getModel().isSelected("Play Again")) {
                game.startGameState();
            } else if (getModel().isSelected("Main Menu")) {
                game.returnToMenu();
            }
        }
    }
}
