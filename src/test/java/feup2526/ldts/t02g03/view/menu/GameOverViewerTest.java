package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.GameOver;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;

class GameOverViewerTest {
    private GameOverViewer viewer;
    private GUI mockGUI;
    private GameOver mockModel;

    @BeforeEach
    void setUp() {
        mockModel = Mockito.mock(GameOver.class);
        viewer = new GameOverViewer(mockModel);
        mockGUI = Mockito.mock(GUI.class);
    }

    @Test
    void testDrawElements() throws Exception {
        Mockito.when(mockModel.getScore()).thenReturn(100);
        Mockito.when(mockModel.getHighScore()).thenReturn(200);
        Mockito.when(mockModel.getCoins()).thenReturn(50);
        Mockito.when(mockModel.getNumberEntries()).thenReturn(2);
        Mockito.when(mockModel.getEntry(0)).thenReturn("Retry");
        Mockito.when(mockModel.getEntry(1)).thenReturn("Exit");
        Mockito.when(mockModel.isSelected(0)).thenReturn(true);
        Mockito.when(mockModel.isSelected(1)).thenReturn(false);

        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), contains("GAME OVER"), anyString());
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), contains("Score: 100"), anyString());
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), contains("High Score: 200"), anyString());
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), contains("Coins: 50"), anyString());
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Retry"), eq("#FFFF00")); // Selected
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Exit"), eq("#FFFFFF")); // Not selected
    }
}
