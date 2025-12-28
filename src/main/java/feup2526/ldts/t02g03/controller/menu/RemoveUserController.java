package feup2526.ldts.t02g03.controller.menu;

import com.googlecode.lanterna.input.KeyStroke;
import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.states.MenuState;
import java.io.IOException;

public class RemoveUserController extends Controller<RemoveUser> {
    public RemoveUserController(RemoveUser model) {
        super(model);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null)
            return;

        if (getModel().isConfirming()) {
            handleConfirmation(game, key);
        } else {
            handleUserSelection(game, key);
        }
    }

    private void handleConfirmation(Game game, KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowLeft:
            case ArrowRight:
                getModel().toggleConfirmOption();
                break;
            case Enter:
                if (getModel().getConfirmOptionIndex() == 0) {
                    User userToRemove = getModel().getUserToRemove();
                    getModel().getUserManager().removeUser(userToRemove.getName());
                    User currentUser = getModel().getParentMenu().getCurrentUser();
                    if (currentUser != null && currentUser.getName().equals(userToRemove.getName())) {
                        if (!getModel().getUsers().isEmpty()) {
                            getModel().getParentMenu().setCurrentUser(getModel().getUsers().get(0));
                            game.setCurrentUser(getModel().getParentMenu().getCurrentUser());
                        } else {
                            getModel().getParentMenu().setCurrentUser(null);
                            game.setCurrentUser(null);
                        }
                    }
                }
                Menu menu = new Menu();
                menu.setUserListActive(true);
                game.setState(new MenuState(menu));
                break;
            case Escape:
                getModel().setConfirming(false);
                break;
            default:
                break;
        }
    }

    private void handleUserSelection(Game game, KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                getModel().previousEntry();
                break;
            case ArrowDown:
                getModel().nextEntry();
                break;
            case Enter:
                getModel().setConfirming(true);
                break;
            case Escape:
                game.returnToMenu();
                break;
            default:
                break;
        }
    }
}
