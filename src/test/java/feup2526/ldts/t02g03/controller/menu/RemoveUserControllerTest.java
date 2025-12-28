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
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    void testRemoveUserLogic() throws IOException {
        when(mockModel.isConfirming()).thenReturn(true);
        // Option 0 = Yes? Assume default index logic based on controller
        when(mockModel.getConfirmOptionIndex()).thenReturn(0);
        
        java.util.ArrayList<String> skins = new java.util.ArrayList<>();
        User userToRemove = new User("ToDelete", 10, 5, "chicken", skins);
        when(mockModel.getUserToRemove()).thenReturn(userToRemove);
        
        // Current user is the one being removed
        when(mockParentMenu.getCurrentUser()).thenReturn(userToRemove);
        
        List<User> userList = new ArrayList<>();
        when(mockModel.getUsers()).thenReturn(userList);
        
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        
        verify(mockUserManager).removeUser("ToDelete");
        // Should set state back to MenuState
        verify(mockGame).setState(any(MenuState.class));
    }
}
