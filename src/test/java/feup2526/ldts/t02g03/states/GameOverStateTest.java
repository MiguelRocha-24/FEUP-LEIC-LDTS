package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.GameOverController;
import feup2526.ldts.t02g03.model.menu.GameOver;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.GameOverViewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOverStateTest {
    @Test
    void testFactoryMethods() {
        GameOver model = Mockito.mock(GameOver.class);
        GameOverState state = new GameOverState(model);

        Viewer<GameOver> viewer = state.createViewer();
        assertTrue(viewer instanceof GameOverViewer);

        Controller<GameOver> controller = state.createController();
        assertTrue(controller instanceof GameOverController);
    }
}
