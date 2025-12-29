package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Coin;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;

class CoinViewerTest {
    private static final int TILESIZE = 16;
    private CoinViewer viewer;
    private GUI mockGUI;
    private GUIImage mockCoin1;
    private GUIImage mockCoin2;
    private GUIImage mockCoin3;
    private GUIImage mockCoin4;

    private class TestableCoinViewer extends CoinViewer {
        private String lastRequestedPath;

        @Override
        public GUIImage getSprite(GUI gui, String path) {
            lastRequestedPath = path;
            if (path.equals("docs/images/sprites/coin1.png"))
                return mockCoin1;
            if (path.equals("docs/images/sprites/coin2.png"))
                return mockCoin2;
            if (path.equals("docs/images/sprites/coin3.png"))
                return mockCoin3;
            return mockCoin4;
        }

        public String getLastRequestedPath() {
            return lastRequestedPath;
        }

        public void drawWithMockTime(GUI gui, Coin coin, int tileSize, int yPos, long currentTimeMs) {
            int frame = (int) ((currentTimeMs / 200) % 4);
            String path = "docs/images/sprites/coin" + (frame + 1) + ".png";
            GUIImage sprite = getSprite(gui, path);
            drawSprite(gui, sprite, (int) (coin.getPosition().getX() * tileSize), yPos);
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockCoin1 = Mockito.mock(GUIImage.class);
        mockCoin2 = Mockito.mock(GUIImage.class);
        mockCoin3 = Mockito.mock(GUIImage.class);
        mockCoin4 = Mockito.mock(GUIImage.class);
        viewer = new TestableCoinViewer();
    }

    @Test
    void testDrawFrame1() {
        Coin coin = new Coin(new Position(5, 5));
        ((TestableCoinViewer) viewer).drawWithMockTime(mockGUI, coin, TILESIZE, 80, 0);
        assert "docs/images/sprites/coin1.png".equals(((TestableCoinViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(80), eq(80), eq(mockCoin1));
    }

    @Test
    void testDrawFrame2() {
        Coin coin = new Coin(new Position(5, 5));
        ((TestableCoinViewer) viewer).drawWithMockTime(mockGUI, coin, TILESIZE, 80, 200);
        assert "docs/images/sprites/coin2.png".equals(((TestableCoinViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(80), eq(80), eq(mockCoin2));
    }

    @Test
    void testDrawFrame3() {
        Coin coin = new Coin(new Position(5, 5));
        ((TestableCoinViewer) viewer).drawWithMockTime(mockGUI, coin, TILESIZE, 80, 400);
        assert "docs/images/sprites/coin3.png".equals(((TestableCoinViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(80), eq(80), eq(mockCoin3));
    }

    @Test
    void testDrawFrame4() {
        Coin coin = new Coin(new Position(5, 5));
        ((TestableCoinViewer) viewer).drawWithMockTime(mockGUI, coin, TILESIZE, 80, 600);
        assert "docs/images/sprites/coin4.png".equals(((TestableCoinViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(80), eq(80), eq(mockCoin4));
    }

    @Test
    void testDrawFrame5() {
        Coin coin = new Coin(new Position(5, 5));
        ((TestableCoinViewer) viewer).drawWithMockTime(mockGUI, coin, TILESIZE, 80, 800);
        assert "docs/images/sprites/coin1.png".equals(((TestableCoinViewer) viewer).getLastRequestedPath());
        Mockito.verify(mockGUI).drawImage(eq(80), eq(80), eq(mockCoin1));
    }
}
