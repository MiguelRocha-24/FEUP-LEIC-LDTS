package feup2526.ldts.t02g03.controller.menu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.view.menu.NewUserViewer;

import java.io.IOException;

public class NewUserController extends Controller<Menu> {
    public NewUserController(Menu model) {
        super(model);
    }

    @Override
    public void step(Game game, KeyStroke key, long time) throws IOException {
        if (key == null)
            return;

        if (key.getKeyType() == KeyType.Enter) {
            String name = NewUserViewer.getCurrentInput();
            if (!name.isEmpty()) {
                getModel().getUserManager().addUser(name);
                User newUser = getModel().getUserManager().getUser(name);
                game.setCurrentUser(newUser);
                getModel().setCurrentUser(newUser);
                getModel().setUserListActive(false);
                NewUserViewer.clearInput();
                game.setState(new MenuState(getModel()));
            }
        } else if (key.getKeyType() == KeyType.Escape) {
            NewUserViewer.clearInput();
            game.setState(new MenuState(getModel()));
        } else if (key.getKeyType() == KeyType.Backspace) {
            NewUserViewer.backspace();
        } else if (key.getKeyType() == KeyType.Character) {
            NewUserViewer.append(key.getCharacter());
        }
    }
}
