package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Coin;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.game.SafeLane;
import feup2526.ldts.t02g03.model.game.Tree;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


class SafeLaneViewerTest {
    private SafeLaneViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableSafeLaneViewer extends SafeLaneViewer {
        public TestableSafeLaneViewer(int width) {
            super(width);
        }
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableSafeLaneViewer(10);
    }

    @Test
    void testDraw() {
        SafeLane mockLane = Mockito.mock(SafeLane.class);
        Tree tree = new Tree(new Position(0, 0));
        Coin coin = new Coin(new Position(1, 0));

        Mockito.when(mockLane.getTrees()).thenReturn(Collections.singletonList(tree));
        Mockito.when(mockLane.getCoins()).thenReturn(Collections.singletonList(coin));
        Mockito.when(mockGUI.createOffScreenImage(anyInt(), anyInt())).thenReturn(mockImage);

        viewer.draw(mockGUI, mockLane, 16, 16);

        // Verify background tiles (width 10) + 1 tree + 1 coin
        Mockito.verify(mockGUI, Mockito.times(10 + 1 + 1)).drawImage(anyInt(), anyInt(), any());
    }
}
