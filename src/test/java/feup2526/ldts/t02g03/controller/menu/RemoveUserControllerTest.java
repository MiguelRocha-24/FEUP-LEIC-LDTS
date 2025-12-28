package feup2526.ldts.t02g03.controller.menu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.states.MenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RemoveUserControllerTest {
    private RemoveUserController controller;
    private RemoveUser mockModel;
    private Game mockGame;
    private UserManager mockUserManager;
    private Menu mockParentMenu;

    @BeforeEach
    void setUp() {
        mockModel = mock(RemoveUser.class);
        mockGame = mock(Game.class);
        mockUserManager = mock(UserManager.class);
        mockParentMenu = mock(Menu.class);

        when(mockModel.getUserManager()).thenReturn(mockUserManager);
        when(mockModel.getParentMenu()).thenReturn(mockParentMenu);
        controller = new RemoveUserController(mockModel);
    }

    @Test
    void testNavigation() throws IOException {
        when(mockModel.isConfirming()).thenReturn(false);

        controller.step(mockGame, new KeyStroke(KeyType.ArrowDown), 0);
        verify(mockModel).nextEntry();

        controller.step(mockGame, new KeyStroke(KeyType.ArrowUp), 0);
        verify(mockModel).previousEntry();

        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        verify(mockModel).setConfirming(true);

        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        verify(mockGame).returnToMenu();
    }

    @Test
    void testConfirmationNavigation() throws IOException {
        when(mockModel.isConfirming()).thenReturn(true);

        controller.step(mockGame, new KeyStroke(KeyType.ArrowRight), 0);
        verify(mockModel).toggleConfirmOption();

        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        verify(mockModel).setConfirming(false);
    }

    @Test
    void testRemoveUserCancel() throws IOException {
        when(mockModel.isConfirming()).thenReturn(true);
        when(mockModel.getConfirmOptionIndex()).thenReturn(1);
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);

        verify(mockUserManager, never()).removeUser(anyString());
        verify(mockGame).setState(any(MenuState.class));
    }

    @Test
    void testRemoveOtherUser() throws IOException {
        when(mockModel.isConfirming()).thenReturn(true);
        when(mockModel.getConfirmOptionIndex()).thenReturn(0);

        User userToRemove = new User("UserToRemove", 10, 5, "skin", new ArrayList<>());
        User currentUser = new User("CurrentUser", 20, 5, "skin", new ArrayList<>());

        when(mockModel.getUserToRemove()).thenReturn(userToRemove);
        when(mockParentMenu.getCurrentUser()).thenReturn(currentUser);

        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);

        verify(mockUserManager).removeUser("UserToRemove");
        verify(mockParentMenu, never()).setCurrentUser(any());
        verify(mockGame, never()).setCurrentUser(any());
        verify(mockGame).setState(any(MenuState.class));
    }

    @Test
    void testRemoveLastUser() throws IOException {
        when(mockModel.isConfirming()).thenReturn(true);
        when(mockModel.getConfirmOptionIndex()).thenReturn(0);
        User userToRemove = new User("LastUser", 10, 5, "skin", new ArrayList<>());
        when(mockModel.getUserToRemove()).thenReturn(userToRemove);
        when(mockParentMenu.getCurrentUser()).thenReturn(userToRemove);
        when(mockModel.getUsers()).thenReturn(new ArrayList<>());
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        verify(mockUserManager).removeUser("LastUser");
        verify(mockParentMenu).setCurrentUser(null);
        verify(mockGame).setCurrentUser(null);
        verify(mockGame).setState(any(MenuState.class));
    }
}
