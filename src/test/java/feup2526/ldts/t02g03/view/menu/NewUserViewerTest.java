package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

class NewUserViewerTest {
    private NewUserViewer viewer;
    private GUI mockGUI;
    private Menu mockModel;

    @BeforeEach
    void setUp() {
        mockModel = Mockito.mock(Menu.class);
        viewer = new NewUserViewer(mockModel);
        mockGUI = Mockito.mock(GUI.class);
        NewUserViewer.clearInput();
    }

    @Test
    void testInputLogic() {
        NewUserViewer.append('A');
        NewUserViewer.append('B');
        assertEquals("AB", NewUserViewer.getCurrentInput());

        NewUserViewer.backspace();
        assertEquals("A", NewUserViewer.getCurrentInput());

        NewUserViewer.clearInput();
        assertEquals("", NewUserViewer.getCurrentInput());
    }

    @Test
    void testDrawElements() throws Exception {
        NewUserViewer.append('T');
        NewUserViewer.append('e');
        NewUserViewer.append('s');
        NewUserViewer.append('t');

        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Enter Name:"), eq("#FFFFFF"));
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Test"), eq("#FFD700"));
    }
}
