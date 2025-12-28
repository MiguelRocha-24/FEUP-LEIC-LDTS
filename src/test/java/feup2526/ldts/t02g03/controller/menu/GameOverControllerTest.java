package feup2526.ldts.t02g03.controller.menu;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.model.menu.GameOver;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;

class GameOverControllerTest {
    @Test
    void testNavigation() throws IOException {
        GameOver mockModel = Mockito.mock(GameOver.class);
        GameOverController controller = new GameOverController(mockModel);
        Game mockGame = Mockito.mock(Game.class);
        controller.step(mockGame, new KeyStroke(KeyType.ArrowUp));
        Mockito.verify(mockModel).previousEntry();
        
        controller.step(mockGame, new KeyStroke(KeyType.ArrowDown));
        Mockito.verify(mockModel).nextEntry();
    }
    
    @Test
    void testSelection() throws IOException {
        GameOver mockModel = Mockito.mock(GameOver.class);
        GameOverController controller = new GameOverController(mockModel);
        Game mockGame = Mockito.mock(Game.class);
        
        Mockito.when(mockModel.isSelected("Play Again")).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.Enter));
        Mockito.verify(mockGame).startGameState();
        
        Mockito.when(mockModel.isSelected("Play Again")).thenReturn(false);
        Mockito.when(mockModel.isSelected("Main Menu")).thenReturn(true);
        controller.step(mockGame, new KeyStroke(KeyType.Enter));
        Mockito.verify(mockGame).returnToMenu();
    }
}
