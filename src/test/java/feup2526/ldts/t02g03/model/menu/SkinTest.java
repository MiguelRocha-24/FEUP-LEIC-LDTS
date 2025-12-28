package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkinTest {
    @Test
    void testSkinLogic() {
        Skin s = new Skin("Test", 100);
        assertEquals("Test", s.getName());
        assertEquals(100, s.getPrice());
        
        s.setName("New");
        s.setPrice(50);
        assertEquals("New", s.getName());
        assertEquals(50, s.getPrice());
    }
}
