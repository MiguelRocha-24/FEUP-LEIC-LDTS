package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    private Shop shop;
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = Mockito.mock(User.class);
        shop = new Shop(mockUser);
    }

    @Test
    void testInitialization() {
        assertEquals(mockUser, shop.getUser());
        assertFalse(shop.getSkins().isEmpty());
    }

    @Test
    void testSelection() {
        assertEquals(0, shop.getSelectedSkinIndex());
        assertTrue(shop.isSelected(0));
        
        shop.nextSkin();
        assertEquals(1, shop.getSelectedSkinIndex());
        
        shop.previousSkin();
        assertEquals(0, shop.getSelectedSkinIndex());
        
        shop.previousSkin(); // wrap around
        assertEquals(shop.getSkins().size() - 1, shop.getSelectedSkinIndex());
    }

    @Test
    void testGetSkin() {
        Skin s = shop.getSkin("chicken");
        assertNotNull(s);
        assertEquals("chicken", s.getName());
    }
}
