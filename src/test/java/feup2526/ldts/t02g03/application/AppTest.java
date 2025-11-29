package feup2526.ldts.t02g03.application;
import feup2526.ldts.t02g03.controller.GameController;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.view.ConsoleViewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.mockito.Mockito.*;


public class AppTest {
    @Test
    void testGameOver() throws IOException{
        Level mockLevel = Mockito.mock(Level.class);
        GameController mockController = Mockito.mock(GameController.class);
        ConsoleViewer mockViewer = Mockito.mock(ConsoleViewer.class);

        // Game over immediately
        when(mockLevel.isGameOver()).thenReturn(true);
        App.run(mockLevel, mockController, mockViewer);
        verify(mockController, times(30)).updateLanes();
        verify(mockViewer, times(1)).draw(mockLevel);
        verify(mockController, never()).updatePlayer();
    }

    @Test
    void testPlayerMove() throws IOException{
        Level mockLevel = Mockito.mock(Level.class);
        GameController mockController = Mockito.mock(GameController.class);
        ConsoleViewer mockViewer = Mockito.mock(ConsoleViewer.class);
        when(mockLevel.isGameOver()).thenReturn(false, true);
        when(mockController.updatePlayer()).thenReturn(true);

        App.run(mockLevel, mockController, mockViewer);
        verify(mockController, times(30)).updateLanes();
        verify(mockViewer, times(2)).draw(mockLevel);
        verify(mockController, times(1)).updatePlayer();
    }

    @Test
    void testPlayerNotUpdated() throws IOException{
        Level mockLevel = Mockito.mock(Level.class);
        GameController mockController = Mockito.mock(GameController.class);
        ConsoleViewer mockViewer = Mockito.mock(ConsoleViewer.class);
        when(mockLevel.isGameOver()).thenReturn(false, true);

        //Wrong input
        when(mockController.updatePlayer()).thenReturn(false);

        App.run(mockLevel, mockController, mockViewer);
        verify(mockController, times(30)).updateLanes();
        verify(mockViewer, times(1)).draw(mockLevel);
        verify(mockController, times(1)).updatePlayer();
    }
}
