package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.Menu;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import feup2526.ldts.t02g03.states.NewUserState;
import feup2526.ldts.t02g03.states.RemoveUserState;
import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.states.ShopState;
import feup2526.ldts.t02g03.model.menu.Shop;

public class MenuController extends Controller<Menu> {
    public MenuController(Menu menu) {
        super(menu);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null)
            return;

        if (getModel().isUserListActive()) {
            switch (key.getKeyType()) {
                case ArrowUp:
                    getModel().previousEntry();
                    break;
                case ArrowDown:
                    getModel().nextEntry();
                    break;
                case Enter:
                    int selectedIndex = getModel().getSelectedUserIndex();
                    int numUsers = getModel().getUserManager().getUsers().size();

                    if (selectedIndex == numUsers) {
                        // "New User" selected
                        game.setState(new NewUserState(getModel()));
                    } else if (selectedIndex == numUsers + 1) {
                        if (!getModel().getUserManager().getUsers().isEmpty()) {
                            game.setState(new RemoveUserState(new RemoveUser(getModel())));
                        }
                    } else {
                        User selectedUser = getModel().getUserManager().getUsers().get(selectedIndex);
                        game.setCurrentUser(selectedUser);
                        getModel().setCurrentUser(selectedUser);
                        getModel().setUserListActive(false);
                    }
                    break;
                case Escape:
                    getModel().setUserListActive(false);
                    break;
                default:
                    break;
            }
        } else {
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
                    if (getModel().isSelected("Start")) {
                        game.setCurrentUser(getModel().getCurrentUser());
                        game.startGameState();
                    }
                    if (getModel().isSelected("Change User"))
                        getModel().setUserListActive(true);
                    if (getModel().isSelected("Shop"))
                        game.setState(new ShopState(new Shop(getModel().getCurrentUser())));
                    break;
                case EOF:
                    game.setState(null);
                    break;
                default:
                    break;
            }
        }
    }
}
