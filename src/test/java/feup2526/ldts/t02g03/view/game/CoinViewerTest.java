package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Coin;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class CoinViewerTest {
    private CoinViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableCoinViewer extends CoinViewer {
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableCoinViewer();
    }

    @Test
    void testDraw() {
        Coin coin = new Coin(new Position(5, 5));
        
        viewer.draw(mockGUI, coin, 10, 50);

        // Position 5 * 10 = 50, yPos 5 * 10 = 50
        Mockito.verify(mockGUI).drawImage(eq(50), eq(50), eq(mockImage));
    }
}
