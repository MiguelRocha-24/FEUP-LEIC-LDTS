package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.Collections;

class MenuViewerTest {
    @Test
    void testDrawElements() throws IOException {
        Menu mockMenu = Mockito.mock(Menu.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        
        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);
        Mockito.when(mockMenu.getNumberEntries()).thenReturn(2);
        Mockito.when(mockMenu.getEntry(0)).thenReturn("Start");
        Mockito.when(mockMenu.getEntry(1)).thenReturn("Exit");
        Mockito.when(mockMenu.isSelected(0)).thenReturn(true);

        User mockUser = new User("Test", 10, 100, "A", null);
        Mockito.when(mockMenu.getCurrentUser()).thenReturn(mockUser);
        
        MenuViewer viewer = new MenuViewer(mockMenu);
        viewer.draw(mockGUI);
        
        Mockito.verify(mockGUI).drawText(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq("CROSSY ROADS"), Mockito.anyString());
        Mockito.verify(mockGUI).drawText(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq("Start"), Mockito.eq("#FFD700"));
        Mockito.verify(mockGUI).drawText(Mockito.anyInt(), Mockito.anyInt(), Mockito.contains("User: Test"), Mockito.anyString());
    }

    @Test
    void testDrawUserList() throws IOException {
        Menu mockMenu = Mockito.mock(Menu.class);
        GUI mockGUI = Mockito.mock(GUI.class);
        UserManager mockUM = Mockito.mock(UserManager.class);
        
        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);
        Mockito.when(mockMenu.isUserListActive()).thenReturn(true);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUM);
        User u = new User("Alice", 0, 0, "A", null);
        Mockito.when(mockUM.getUsers()).thenReturn(Collections.singletonList(u));
        
        MenuViewer viewer = new MenuViewer(mockMenu);
        viewer.draw(mockGUI);
        
        Mockito.verify(mockGUI).drawText(Mockito.anyInt(), Mockito.anyInt(), Mockito.contains("Alice"), Mockito.anyString());
        Mockito.verify(mockGUI).drawText(Mockito.anyInt(), Mockito.anyInt(), Mockito.contains("New User"), Mockito.anyString());
    }
}
