package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.User;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.ArrayList;

class ShopControllerTest {
    @Test
    void testNavigation() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        ShopController controller = new ShopController(mockShop);
        Game mockGame = Mockito.mock(Game.class);
        
        controller.step(mockGame, new KeyStroke(KeyType.ArrowRight), 0);
        Mockito.verify(mockShop).nextSkin();
        
        controller.step(mockGame, new KeyStroke(KeyType.ArrowLeft), 0);
        Mockito.verify(mockShop).previousSkin();
    }
    
    // Testing purchase logic is hard because ShopController creates a new UserManager() internally
    // which leads to file I/O. We'll skip tests that trigger processSelection to avoid side effects
    // or needing complex file mocking, adhering to the "no main code changes" constraint.
    // If we tested it, we'd need to mock the entire environment.
    
    @Test
    void testReturnToMenu() throws IOException {
        Shop mockShop = Mockito.mock(Shop.class);
        ShopController controller = new ShopController(mockShop);
        Game mockGame = Mockito.mock(Game.class);
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockShop.getUser()).thenReturn(mockUser);
        
        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        
        Mockito.verify(mockGame).setState(Mockito.any());
    }
}
