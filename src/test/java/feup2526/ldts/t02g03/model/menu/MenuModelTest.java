package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuModelTest {
    @Test
    void testNavigation() {
        Menu menu = new Menu();
        // Default strict mode might fail if no users exist or file issues. 
        // But logic for nextEntry/previousEntry is testable.
        
        int initialMain = 0;
        // Assuming not in user list
        assertFalse(menu.isUserListActive());
        
        menu.nextEntry();
        assertFalse(menu.isSelected(initialMain));
        assertTrue(menu.isSelected(initialMain + 1));
        
        menu.previousEntry();
        assertTrue(menu.isSelected(initialMain));
    }
    
    @Test
    void testUserListToggle() {
        Menu menu = new Menu();
        menu.setUserListActive(true);
        assertTrue(menu.isUserListActive());
        assertEquals(0, menu.getSelectedUserIndex());
    }
}
