package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.model.menu.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuStateTest {

    @Test
    void testInitialization() {
        Menu menu = new Menu();
        MenuState menuState = new MenuState(menu);

        assertNotNull(menuState.getModel());
        assertSame(menu, menuState.getModel());
    }
}
