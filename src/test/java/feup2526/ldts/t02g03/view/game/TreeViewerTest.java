package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.game.Tree;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;

class TreeViewerTest {
    private TreeViewer viewer;
    private GUI mockGUI;
    private GUIImage mockImage;

    private class TestableTreeViewer extends TreeViewer {
        @Override
        public GUIImage getSprite(GUI gui, String path) {
            return mockImage;
        }
    }

    @BeforeEach
    void setUp() {
        mockGUI = Mockito.mock(GUI.class);
        mockImage = Mockito.mock(GUIImage.class);
        viewer = new TestableTreeViewer();
    }

    @Test
    void testDraw() {
        Tree tree = new Tree(new Position(4, 4));
        viewer.draw(mockGUI, tree, 10, 40);
        Mockito.verify(mockGUI).drawImage(eq(40), eq(40), eq(mockImage));
    }
}
