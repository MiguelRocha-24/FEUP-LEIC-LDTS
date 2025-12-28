package feup2526.ldts.t02g03.controller.menu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import feup2526.ldts.t02g03.view.menu.NewUserViewer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewUserControllerTest {
    private NewUserController controller;
    private Menu mockMenu;
    private Game mockGame;
    private UserManager mockUserManager;

    @BeforeEach
    void setUp() {
        mockMenu = mock(Menu.class);
        mockGame = mock(Game.class);
        mockUserManager = mock(UserManager.class);
        
        when(mockMenu.getUserManager()).thenReturn(mockUserManager);
        controller = new NewUserController(mockMenu);
        
        // Ensure clean state
        NewUserViewer.clearInput();
    }

    @AfterEach
    void tearDown() {
        NewUserViewer.clearInput();
    }

    @Test
    void testEnterCreatesUser() throws IOException {
        // Setup state manually
        NewUserViewer.append('T');
        NewUserViewer.append('e');
        NewUserViewer.append('s');
        NewUserViewer.append('t');
        
        java.util.ArrayList<String> skins = new java.util.ArrayList<>();
        skins.add("chicken");
        User mockUser = new User("Test", 0, 0, "chicken", skins);
        
        when(mockUserManager.getUser("Test")).thenReturn(mockUser);
        
        controller.step(mockGame, new KeyStroke(KeyType.Enter), 0);
        
        verify(mockUserManager).addUser("Test");
        verify(mockGame).setCurrentUser(any(User.class));
        verify(mockGame).returnToMenu();
        
        assertEquals("", NewUserViewer.getCurrentInput());
    }
    
    @Test
    void testEscape() throws IOException {
        NewUserViewer.append('A');
        
        controller.step(mockGame, new KeyStroke(KeyType.Escape), 0);
        
        assertEquals("", NewUserViewer.getCurrentInput());
        verify(mockGame).returnToMenu();
    }
    
    @Test
    void testTyping() throws IOException {
        controller.step(mockGame, new KeyStroke('a', false, false), 0);
        assertEquals("a", NewUserViewer.getCurrentInput());
    }
    
    @Test
    void testBackspace() throws IOException {
        NewUserViewer.append('a');
        controller.step(mockGame, new KeyStroke(KeyType.Backspace), 0);
        assertEquals("", NewUserViewer.getCurrentInput());
    }
}
