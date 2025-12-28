package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.ShopController;
import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.ShopViewer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopStateTest {
    @Test
    void testFactoryMethods() {
        Shop model = Mockito.mock(Shop.class);
        ShopState state = new ShopState(model);

        Viewer<Shop> viewer = state.createViewer();
        assertTrue(viewer instanceof ShopViewer);

        Controller<Shop> controller = state.createController();
        assertTrue(controller instanceof ShopController);
    }
}
