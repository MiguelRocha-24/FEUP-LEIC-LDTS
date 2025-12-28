package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserLogic() {
        ArrayList<String> skins = new ArrayList<>(Arrays.asList("A", "B"));
        User u = new User("Name", 10, 100, "A", skins);

        assertEquals("Name", u.getName());
        assertEquals(10, u.getCoins());
        assertEquals(100, u.getHighScore());
        assertEquals("A", u.getEquippedSkin());
        assertEquals(skins, u.getOwnedSkins());

        u.setCoins(20);
        u.setHighScore(200);
        u.setEquippedSkin("B");
        assertEquals(20, u.getCoins());
        assertEquals(200, u.getHighScore());
        assertEquals("B", u.getEquippedSkin());
        
        assertTrue(u.toString().contains("Name"));
    }
}
