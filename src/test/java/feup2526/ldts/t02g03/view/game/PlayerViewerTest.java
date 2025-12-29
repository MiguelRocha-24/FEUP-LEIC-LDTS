package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerViewerTest {

    private static class TestablePlayerViewer extends PlayerViewer {
        private final GUIImage mockImage;
        private String lastRequestedPath;

        public TestablePlayerViewer(GUIImage mockImage) {
            super();
            this.mockImage = mockImage;
        }

        @Override
        protected GUIImage getSprite(GUI gui, String path) {
            this.lastRequestedPath = path;
            return mockImage;
        }
        
        public String getLastRequestedPath() {
            return lastRequestedPath;
        }
    }

    @Test
    void testDrawNormal() {
        Player mockPlayer = Mockito.mock(Player.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        when(mockPlayer.getPosition()).thenReturn(new Position(5, 5));
        when(mockPlayer.getDirection()).thenReturn(Direction.RIGHT);
        
        TestablePlayerViewer viewer = new TestablePlayerViewer(mockImage);

        viewer.draw(mockGUI, mockPlayer, 16, 50);

        verify(mockGUI).drawImage(eq(5 * 16), eq(50 - 1), eq(mockImage));
        assertEquals("docs/images/sprites/chickenRight.png", viewer.getLastRequestedPath());
    }
    
    @Test
    void testDrawDeadCollision() {
        Player mockPlayer = Mockito.mock(Player.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);

        when(mockPlayer.getPosition()).thenReturn(new Position(5, 5));
        when(mockPlayer.getDirection()).thenReturn(Direction.LEFT);
        
        TestablePlayerViewer viewer = new TestablePlayerViewer(mockImage);

        long collisionTime = System.currentTimeMillis();
        viewer.draw(mockGUI, mockPlayer, 16, 50, true, collisionTime);

        verify(mockGUI).drawImage(eq(5 * 16), eq(50 - 1), eq(mockImage));
        assertEquals("docs/images/sprites/chickenLeftDead.png", viewer.getLastRequestedPath());
    }

    @Test
    void testSkinChange() {
        Player mockPlayer = Mockito.mock(Player.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        GUIImage mockImage = Mockito.mock(GUIImage.class);
        
        when(mockPlayer.getPosition()).thenReturn(new Position(0,0));
        when(mockPlayer.getDirection()).thenReturn(Direction.RIGHT);

        TestablePlayerViewer viewer = new TestablePlayerViewer(mockImage);
        viewer.setSkinName("dog");
        
        viewer.draw(mockGUI, mockPlayer, 16, 0);
        
        assertEquals("docs/images/sprites/dogRight.png", viewer.getLastRequestedPath());
    }
}
