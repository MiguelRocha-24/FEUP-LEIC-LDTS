package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.RemoveUserController;
import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.RemoveUserViewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoveUserStateTest {
    @Test
    void testFactoryMethods() {
        RemoveUser model = Mockito.mock(RemoveUser.class);
        RemoveUserState state = new RemoveUserState(model);

        Viewer<RemoveUser> viewer = state.createViewer();
        assertTrue(viewer instanceof RemoveUserViewer);

        Controller<RemoveUser> controller = state.createController();
        assertTrue(controller instanceof RemoveUserController);
    }
}
