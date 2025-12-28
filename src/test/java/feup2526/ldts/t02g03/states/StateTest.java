package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.application.Game;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StateTest {

    @Test
    void testStep() throws IOException {
        // Create a stub concrete State
        Controller<Object> mockController = mock(Controller.class);
        Viewer<Object> mockViewer = mock(Viewer.class);
        Object mockModel = new Object();
        
        State<Object> state = new State<>(mockModel) {
            @Override
            protected Viewer<Object> createViewer() {
                return mockViewer;
            }

            @Override
            protected Controller<Object> createController() {
                return mockController;
            }
        };

        Game mockGame = mock(Game.class);
        GUI mockGUI = mock(GUI.class);
        long time = 1000L;

        // Execute step
        state.step(mockGame, mockGUI, time);

        // Verify that controller.step and viewer.draw were called
        verify(mockController).step(mockGame, mockGUI.readInput(), time);
        verify(mockViewer).draw(mockGUI);
    }
}
