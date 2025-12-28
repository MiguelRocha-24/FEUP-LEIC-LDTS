package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

class MenuControllerTest {
    private Menu mockMenu;
    private MenuController controller;
    private Game mockGame;

    @BeforeEach
    void setUp() {
        mockMenu = Mockito.mock(Menu.class);
        controller = new MenuController(mockMenu);
        mockGame = Mockito.mock(Game.class);
    }

    @Test
    void testNullKeyHandling() throws IOException {
        controller.step(mockGame, null, 0);
        Mockito.verifyNoInteractions(mockGame);
    }

    @Test
    void testNavigation() throws IOException {
        // Test ArrowDown -> nextEntry
        controller.step(mockGame, new KeyStroke(KeyType.ArrowDown), 0);
        Mockito.verify(mockMenu).nextEntry();
        // Test ArrowUp -> previousEntry
        controller.step(mockGame, new KeyStroke(KeyType.ArrowUp), 0);
        Mockito.verify(mockMenu).previousEntry();
    }

    @Test
    void testEnterStart() throws IOException {
        Mockito.when(mockMenu.isSelected("Start")).thenReturn(true);
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockMenu.getCurrentUser()).thenReturn(mockUser);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setCurrentUser(mockUser);
        Mockito.verify(mockGame).startGameState();
    }

    @Test
    void testEnterExit() throws IOException {
        // Test Exit option
        Mockito.when(mockMenu.isSelected("Exit")).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setState(null);
    }

    @Test
    void testEnterChangeUser() throws IOException {
        Mockito.when(mockMenu.isSelected("Change User")).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockMenu).setUserListActive(true);
    }

    @Test
    void testEnterShop() throws IOException {
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockMenu.isSelected("Shop")).thenReturn(true);
        Mockito.when(mockMenu.getCurrentUser()).thenReturn(mockUser);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setState(any());
    }

    @Test
    void testUserListNavigation() throws IOException {
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.ArrowDown), 0);
        Mockito.verify(mockMenu).nextEntry();
        controller.step(mockGame, new KeyStroke(KeyType.ArrowUp), 0);
        Mockito.verify(mockMenu).previousEntry();
    }

    @Test
    void testUserListSelectExistingUser() throws IOException {
        UserManager mockUserManager = Mockito.mock(UserManager.class);
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        Mockito.when(mockMenu.getSelectedUserIndex()).thenReturn(0);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUserManager);
        Mockito.when(mockUserManager.getUsers()).thenReturn(Arrays.asList(mockUser));

        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setCurrentUser(mockUser);
        Mockito.verify(mockMenu).setCurrentUser(mockUser);
        Mockito.verify(mockMenu).setUserListActive(false);
    }

    @Test
    void testUserListSelectNewUser() throws IOException {
        UserManager mockUserManager = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        Mockito.when(mockMenu.getSelectedUserIndex()).thenReturn(2);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUserManager);
        Mockito.when(mockUserManager.getUsers()).thenReturn(Arrays.asList(Mockito.mock(User.class), Mockito.mock(User.class)));
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setState(any());
    }

    @Test
    void testUserListSelectRemoveUser() throws IOException {
        UserManager mockUserManager = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        Mockito.when(mockMenu.getSelectedUserIndex()).thenReturn(3);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUserManager);
        Mockito.when(mockUserManager.getUsers()).thenReturn(Arrays.asList(Mockito.mock(User.class), Mockito.mock(User.class)));
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame).setState(any());
    }

    @Test
    void testUserListSelectRemoveUserWhenEmpty() throws IOException {
        UserManager mockUserManager = Mockito.mock(UserManager.class);

        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        Mockito.when(mockMenu.getSelectedUserIndex()).thenReturn(1);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUserManager);
        Mockito.when(mockUserManager.getUsers()).thenReturn(new ArrayList<>());

        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        Mockito.verify(mockGame, Mockito.never()).setState(any());
    }

    @Test
    void testUserListEscape() throws IOException {
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        Mockito.verify(mockMenu).setUserListActive(false);
    }
}
