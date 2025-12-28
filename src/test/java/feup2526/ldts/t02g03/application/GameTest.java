package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.LanternaGUIFactory;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    private LanternaGUIFactory mockFactory;
    private GUI mockMenuGUI;
    private GUI mockGameGUI;

    @BeforeEach
    void setUp() {
        mockFactory = mock(LanternaGUIFactory.class);
        mockMenuGUI = mock(GUI.class);
        mockGameGUI = mock(GUI.class);
    }

    @Test
    void testConstructorCreatesMenuGUI() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        new Game(mockFactory);
        verify(mockFactory, times(1)).createMenuGUI();
    }

    @Test
    void testExitGameSetsStateToNull() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        Game game = new Game(mockFactory);
        game.setState(null);
        verify(mockFactory, times(1)).createMenuGUI();
        verifyNoMoreInteractions(mockFactory);
    }

    @Test
    void testGetCurrentUserInitiallyNull() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        Game game = new Game(mockFactory);
        assertNull(game.getCurrentUser());
    }

    @Test
    void testSetCurrentUser() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        Game game = new Game(mockFactory);
        User mockUser = mock(User.class);
        game.setCurrentUser(mockUser);
        assertEquals(mockUser, game.getCurrentUser());
    }

    @Test
    void testReturnToMenu() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        Game game = new Game(mockFactory);
        UserManager userManager = new UserManager();
        User user = userManager.getUser("TestUser");
        game.setCurrentUser(user);
        game.returnToMenu();
        verify(mockFactory, times(1)).createMenuGUI();
    }

    @Test
    void testStartGameClosesGUI() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        when(mockFactory.createGameGUI()).thenReturn(mockGameGUI);
        when(mockFactory.getGridWidth(mockGameGUI)).thenReturn(20);
        when(mockFactory.getGridHeight(mockGameGUI)).thenReturn(20);
        Game game = new Game(mockFactory);
        game.startGameState();
        verify(mockMenuGUI, times(1)).close();
    }

    @Test
    void testStartGameStateCreatesGameGUI() throws Exception {
        when(mockFactory.createMenuGUI()).thenReturn(mockMenuGUI);
        when(mockFactory.createGameGUI()).thenReturn(mockGameGUI);
        when(mockFactory.getGridWidth(mockGameGUI)).thenReturn(20);
        when(mockFactory.getGridHeight(mockGameGUI)).thenReturn(20);
        Game game = new Game(mockFactory);
        game.startGameState();
        verify(mockFactory, times(1)).createGameGUI();
    }
}
