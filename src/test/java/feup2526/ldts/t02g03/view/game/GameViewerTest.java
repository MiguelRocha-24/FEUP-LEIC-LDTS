package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;

class GameViewerTest {
    @Test
    void testDrawElements() throws IOException {
        Level level = new Level(10, 20); // Width 10, Height 20
        // Setup some lanes
        // Level constructor populates lanes? Yes.
        
        GameViewer viewer = new GameViewer(level);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);
        Mockito.when(mockGUI.createOffScreenImage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockImage);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(400); // TILE_SIZE 16 -> 25 rows
        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(300);
        
        // Ensure player is somewhere visible
        level.getPlayer().setPosition(new Position(5, 1));
        
        viewer.draw(mockGUI);
        
        // Verify that lanes were drawn (sub-viewers use drawImage)
        // Since we cannot mock sub-viewers easily, we rely on them not crashing and calling GUI.
        // If they find no images, they don't drawImage.
        // If they find images (maybe from docs folder), they draw.
        // We can verify clear() and refresh() from Viewer base.
        Mockito.verify(mockGUI).clear();
        Mockito.verify(mockGUI).refresh();
        
        // We can also check NumberViewer (score) calls.
        // NumberViewer uses drawImage too.
    }
}
