package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.game.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameStateTest {

    @Test
    void testInitialization() {
        Game mockGame = mock(Game.class);
        when(mockGame.getTerminalGridWidth()).thenReturn(20);
        when(mockGame.getTerminalGridHeight()).thenReturn(20);

        GameState gameState = new GameState(mockGame);

        assertNotNull(gameState.getModel());
        assertTrue(gameState.getModel() instanceof Level);
        assertEquals(21, gameState.getModel().getGrid().getW()); // Width + 1
    }
}
