package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.states.GameState;
import feup2526.ldts.t02g03.states.MenuState;
import feup2526.ldts.t02g03.states.State;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.LanternaGUIFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;

class GameTest {
    @Test
    void testConstructorSetsInitialState() throws Exception {
        LanternaGUIFactory mockFactory = Mockito.mock(LanternaGUIFactory.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        Mockito.when(mockFactory.createMenuGUI()).thenReturn(mockGUI);
        
        Game game = new Game(mockFactory);
        
        // Cannot access state directly, but can try step().
        // Or setter/getter if available. Game only has setState.
        // But constructor sets new MenuState.
        // We can verify mockFactory.createMenuGUI was called.
        Mockito.verify(mockFactory).createMenuGUI();
    }

    @Test
    void testSetState() throws Exception {
        LanternaGUIFactory mockFactory = Mockito.mock(LanternaGUIFactory.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        Mockito.when(mockFactory.createMenuGUI()).thenReturn(mockGUI);
        
        Game game = new Game(mockFactory);
        State mockState = Mockito.mock(State.class);
        
        game.setState(mockState);
        // If state is not null, loop runs. Use null to stop.
        // We can verify behavior of transition.
        
        // Transition from Menu to null.
        // Logic: if currentIsMenu && !nextIsMenu -> close gui.
        // Next is null (neither).
        // If passed mockState is MenuState...
    }
    
    @Test
    void testExit() throws Exception {
        LanternaGUIFactory mockFactory = Mockito.mock(LanternaGUIFactory.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        Mockito.when(mockFactory.createMenuGUI()).thenReturn(mockGUI);
        
        Game game = new Game(mockFactory);
        game.setState(null);
        // Should not throw.
    }
}
