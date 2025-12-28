package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class MenuControllerTest {
    @Test
    void testNavigation() throws IOException {
        Menu mockMenu = Mockito.mock(Menu.class);
        MenuController controller = new MenuController(mockMenu);
        Game mockGame = Mockito.mock(Game.class);
        
        // Test ArrowDown -> nextEntry
        controller.step(mockGame, new KeyStroke(KeyType.ArrowDown), 0);
        Mockito.verify(mockMenu).nextEntry();
        
        // Test ArrowUp -> previousEntry
        controller.step(mockGame, new KeyStroke(KeyType.ArrowUp), 0);
        Mockito.verify(mockMenu).previousEntry();
    }

    @Test
    void testEnterStart() throws IOException {
        Menu mockMenu = Mockito.mock(Menu.class);
        MenuController controller = new MenuController(mockMenu);
        Game mockGame = Mockito.mock(Game.class);
        
        Mockito.when(mockMenu.isSelected("Start")).thenReturn(true);
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockMenu.getCurrentUser()).thenReturn(mockUser);
        
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        
        Mockito.verify(mockGame).setCurrentUser(mockUser);
        Mockito.verify(mockGame).startGameState();
    }

    @Test
    void testEnterExit() throws IOException {
        Menu mockMenu = Mockito.mock(Menu.class);
        MenuController controller = new MenuController(mockMenu);
        Game mockGame = Mockito.mock(Game.class);
        
        Mockito.when(mockMenu.isSelected("Exit")).thenReturn(true);
        
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        
        Mockito.verify(mockGame).setState(null);
    }
}
