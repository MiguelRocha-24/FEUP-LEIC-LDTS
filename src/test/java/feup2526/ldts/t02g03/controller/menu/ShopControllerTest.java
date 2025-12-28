package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.UserManager;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.ArrayList;

class ShopControllerTest {
    private Shop mockShop;
    private ShopController controller;
    private Game mockGame;
    private User mockUser;
    private Skin mockSkin;
    private UserManager mockUserManager;

    @BeforeEach
    void setUp() {
        mockShop = Mockito.mock(Shop.class);
        mockGame = Mockito.mock(Game.class);
        mockUser = Mockito.mock(User.class);
        mockSkin = Mockito.mock(Skin.class);
        mockUserManager = Mockito.mock(UserManager.class);
        controller = Mockito.spy(new ShopController(mockShop));
        Mockito.doReturn(mockUserManager).when(controller).createUserManager();
        Mockito.when(mockShop.getUser()).thenReturn(mockUser);
        Mockito.when(mockShop.getSelectedSkin()).thenReturn(mockSkin);
    }

    @Test
    void testNavigation() throws IOException {
        controller.step(mockGame, new KeyStroke(KeyType.ArrowRight), 0);
        Mockito.verify(mockShop).nextSkin();
        controller.step(mockGame, new KeyStroke(KeyType.ArrowLeft), 0);
        Mockito.verify(mockShop).previousSkin();
    }

    @Test
    void testReturnToMenu() throws IOException {
        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        Mockito.verify(mockGame).setState(Mockito.any());
    }

    @Test
    void testEquipOwnedSkin() throws IOException {
        Mockito.when(mockSkin.getName()).thenReturn("chicken");
        ArrayList<String> owned = new ArrayList<>();
        owned.add("chicken");

        Mockito.when(mockUser.getOwnedSkins()).thenReturn(owned);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockUser).setEquippedSkin("chicken");
        Mockito.verify(mockUserManager).updateUser(mockUser);
    }

    @Test
    void testBuySkinSuccess() throws IOException {
        Mockito.when(mockSkin.getName()).thenReturn("rabbit");
        Mockito.when(mockSkin.getPrice()).thenReturn(100);
        Mockito.when(mockUser.getCoins()).thenReturn(200);

        ArrayList<String> owned = new ArrayList<>();
        Mockito.when(mockUser.getOwnedSkins()).thenReturn(owned);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);

        Mockito.verify(mockUser).setCoins(100);
        assert (owned.contains("rabbit"));
        Mockito.verify(mockUserManager).updateUser(mockUser);
    }

    @Test
    void testBuySkinInsufficientCoins() throws IOException {
        Mockito.when(mockSkin.getName()).thenReturn("rabbit");
        Mockito.when(mockSkin.getPrice()).thenReturn(500);
        Mockito.when(mockUser.getCoins()).thenReturn(50);
        Mockito.when(mockUser.getOwnedSkins()).thenReturn(new ArrayList<>());

        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);

        Mockito.verify(mockUser, Mockito.never()).setCoins(Mockito.anyInt());
        Mockito.verify(mockUser, Mockito.never()).setEquippedSkin(Mockito.anyString());
        Mockito.verify(mockUserManager, Mockito.never()).updateUser(Mockito.any());
    }
}
