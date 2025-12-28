package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import feup2526.ldts.t02g03.states.MenuState;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class ShopController extends Controller<Shop> {
    public ShopController(Shop shop) {
        super(shop);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null)
            return;

        switch (key.getKeyType()) {
            case ArrowLeft:
                getModel().previousSkin();
                break;
            case ArrowRight:
                getModel().nextSkin();
                break;
            case Enter:
                processSelection(game);
                break;
            case Escape:
                returnToMenu(game);
                break;
            default:
                break;
        }
    }

    private void processSelection(Game game) {
        Shop shop = getModel();
        User user = shop.getUser();
        Skin selectedSkin = shop.getSelectedSkin();
        boolean changed = false;

        if (user.getOwnedSkins().contains(selectedSkin.getName())) {
            user.setEquippedSkin(selectedSkin.getName());
            changed = true;
        } else {
            if (user.getCoins() >= selectedSkin.getPrice()) {
                user.setCoins(user.getCoins() - selectedSkin.getPrice());
                user.getOwnedSkins().add(selectedSkin.getName());
                changed = true;
            }
        }

        if (changed) {
            UserManager userManager = createUserManager();
            userManager.updateUser(user);
        }
    }

    protected UserManager createUserManager() {
        return new UserManager();
    }

    private void returnToMenu(Game game) {
        Menu menu = new Menu();
        menu.setCurrentUser(getModel().getUser());
        game.setState(new MenuState(menu));
    }
}
