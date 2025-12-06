package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.Menu;
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
                    game.startGameState();
                break;
            case EOF:
                game.setState(null);
                break;
            default:
                break;
        }
    }
}
