package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.NewUserController;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.NewUserViewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NewUserStateTest {
    @Test
    void testFactoryMethods() {
        Menu model = Mockito.mock(Menu.class);
        NewUserState state = new NewUserState(model);

        Viewer<Menu> viewer = state.createViewer();
        assertTrue(viewer instanceof NewUserViewer);

        Controller<Menu> controller = state.createController();
        assertTrue(controller instanceof NewUserController);
    }
}
