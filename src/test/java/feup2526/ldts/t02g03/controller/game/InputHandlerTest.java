package feup2526.ldts.t02g03.controller.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    @Test
    void testAllMovementKeys() {
        Level mockLevel = Mockito.mock(Level.class);
        PlayerController mockPC = Mockito.mock(PlayerController.class);
        Map<Class<?>, LaneController> mockMap = new HashMap<>();
        Player mockPlayer = Mockito.mock(Player.class);
        Mockito.when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockPlayer.getPosition()).thenReturn(new Position(5, 5));
        Mockito.when(mockLevel.getGrid()).thenReturn(new Grid(10, 10));
        Lane mockLane = Mockito.mock(Lane.class);
        Mockito.when(mockLevel.getLane(Mockito.anyInt())).thenReturn(mockLane);
        InputHandler ih = new InputHandler(mockLevel, mockPC, mockMap);

        ih.handleInput(new KeyStroke(KeyType.ArrowUp));
        Mockito.verify(mockPlayer).setDirection(Direction.UP);
        Mockito.verify(mockPC).moveTo(new Position(5.0, 4.0));

        ih.handleInput(new KeyStroke(KeyType.ArrowDown));
        Mockito.verify(mockPlayer).setDirection(Direction.DOWN);
        Mockito.verify(mockPC).moveTo(new Position(5.0, 6.0));

        ih.handleInput(new KeyStroke(KeyType.ArrowLeft));
        Mockito.verify(mockPlayer).setDirection(Direction.LEFT);
        Mockito.verify(mockPC).moveTo(new Position(4.0, 5.0));

        ih.handleInput(new KeyStroke(KeyType.ArrowRight));
        Mockito.verify(mockPlayer).setDirection(Direction.RIGHT);
        Mockito.verify(mockPC).moveTo(new Position(6.0, 5.0));
        
        ih.handleInput(new KeyStroke('w', false, false));
        Mockito.verify(mockPlayer, Mockito.times(2)).setDirection(Direction.UP);
        Mockito.verify(mockPC, Mockito.times(2)).moveTo(new Position(5.0, 4.0));

        ih.handleInput(new KeyStroke('s', false, false));
        Mockito.verify(mockPlayer, Mockito.times(2)).setDirection(Direction.DOWN);
        Mockito.verify(mockPC, Mockito.times(2)).moveTo(new Position(5.0, 6.0));

        ih.handleInput(new KeyStroke('a', false, false));
        Mockito.verify(mockPlayer, Mockito.times(2)).setDirection(Direction.LEFT);
        Mockito.verify(mockPC, Mockito.times(2)).moveTo(new Position(4.0, 5.0));
        
        ih.handleInput(new KeyStroke('d', false, false));
        Mockito.verify(mockPlayer, Mockito.times(2)).setDirection(Direction.RIGHT);
        Mockito.verify(mockPC, Mockito.times(2)).moveTo(new Position(6.0, 5.0));
    }
}
