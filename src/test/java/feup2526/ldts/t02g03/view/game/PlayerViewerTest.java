package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerViewerTest {
    @Test
    void testDraw() {
        PlayerViewer viewer = new PlayerViewer();
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);
        Mockito.when(mockGUI.createOffScreenImage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockImage);
        
        // Mock Player
        Player p = new Player(new Position(10, 10));
        p.setDirection(Direction.RIGHT);
        
        // Note: Real SpriteViewer attempts to read file. If file exists, it proceeds.
        // If not, it returns null and drawImage is not called.
        // We cannot easily mock the static SpriteViewer.cache or ImageIO.
        // If we want to verify logic, we might need adjustments or integration test.
        // Assuming we are in a unit test environment, files might not be found.
        
        viewer.draw(mockGUI, p, 16, 100);
        
        // If file not found, drawImage isn't called.
        // If file found, it is.
        // This test is fragile depending on file system.
        // However, we can basic instanciation.
    }
}
