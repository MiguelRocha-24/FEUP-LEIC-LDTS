package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.states.GameState;
import feup2526.ldts.t02g03.model.Level;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public class MenuController extends Controller<Menu> {
    public MenuController(Menu menu) {
        super(menu);
    }

    @Override
    public void step(Game game, KeyStroke key, long time) throws IOException {
        if (key == null)
            return;

        switch (key.getKeyType()) {
            case ArrowUp:
                getModel().previousEntry();
                break;
            case ArrowDown:
                getModel().nextEntry();
                break;
            case Enter:
                if (getModel().isSelected("Exit"))
                    game.setState(null);
                if (getModel().isSelected("Start"))
                    game.setState(new GameState(new Level(game.getGridWidth(), game.getGridHeight())));
                break;
            case EOF:
                game.setState(null);
                break;
            default:
                break;
        }
    }
}
