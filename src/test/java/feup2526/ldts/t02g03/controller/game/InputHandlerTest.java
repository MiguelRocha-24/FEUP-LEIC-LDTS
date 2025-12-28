package feup2526.ldts.t02g03.controller.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class InputHandlerTest {
    @Test
    void testQuit() {
        Level mockLevel = Mockito.mock(Level.class);
        InputHandler ih = new InputHandler(mockLevel, null, null);
        
        ih.handleInput(new KeyStroke(KeyType.Escape));
        Mockito.verify(mockLevel).quit();
    }

    @Test
    void testMove() {
        Level mockLevel = Mockito.mock(Level.class);
        PlayerController mockPC = Mockito.mock(PlayerController.class);
        Map<Class<?>, LaneController> mockMap = new HashMap<>();
        
        Player mockPlayer = Mockito.mock(Player.class);
        Mockito.when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockPlayer.getPosition()).thenReturn(new Position(5, 5));
        Mockito.when(mockLevel.getGrid()).thenReturn(new Grid(10, 10)); // W=10
        
        // Mock Destination Lane
        Lane mockLane = Mockito.mock(Lane.class);
        Mockito.when(mockLevel.getLane(4)).thenReturn(mockLane);
        
        InputHandler ih = new InputHandler(mockLevel, mockPC, mockMap);
        
        ih.handleInput(new KeyStroke(KeyType.ArrowUp));
        
        Mockito.verify(mockPlayer).setDirection(Direction.UP);
        Mockito.verify(mockPC).moveTo(Mockito.any(Position.class));
    }
}
