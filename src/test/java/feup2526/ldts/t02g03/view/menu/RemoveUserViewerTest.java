package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;

class RemoveUserViewerTest {
    private RemoveUserViewer viewer;
    private GUI mockGUI;
    private RemoveUser mockModel;

    @BeforeEach
    void setUp() {
        mockModel = Mockito.mock(RemoveUser.class);
        viewer = new RemoveUserViewer(mockModel);
        mockGUI = Mockito.mock(GUI.class);
    }

    @Test
    void testDrawList() throws Exception {
        User user = new User("Alice", 100, 50, "default", new java.util.ArrayList<>());
        Mockito.when(mockModel.isConfirming()).thenReturn(false);
        Mockito.when(mockModel.getUsers()).thenReturn(Collections.singletonList(user));
        Mockito.when(mockModel.getSelectedIndex()).thenReturn(0);

        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("CROSSY ROADS"), eq("#FFD700"));
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Select user to remove:"), eq("#FFFFFF"));
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), contains("Alice"), eq("#FFD700")); // Selected
    }

    @Test
    void testDrawConfirmation() throws Exception {
        User user = new User("Bob", 200, 100, "default", new java.util.ArrayList<>());
        Mockito.when(mockModel.isConfirming()).thenReturn(true);
        Mockito.when(mockModel.getUserToRemove()).thenReturn(user);
        Mockito.when(mockModel.getConfirmOptionIndex()).thenReturn(0); // Yes selected

        Mockito.when(mockGUI.getTerminalWidth()).thenReturn(80);
        Mockito.when(mockGUI.getTerminalHeight()).thenReturn(24);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("CROSSY ROADS"), eq("#FFD700"));
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Remove Bob?"), eq("#FFFFFF"));
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("Yes"), eq("#FFD700")); // Selected
        Mockito.verify(mockGUI).drawText(anyInt(), anyInt(), eq("No"), eq("#FFFFFF"));
    }
}
