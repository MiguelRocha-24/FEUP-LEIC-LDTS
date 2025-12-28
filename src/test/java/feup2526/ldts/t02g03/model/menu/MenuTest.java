package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test
    void testConstructor() {
        Menu menu = new Menu();
        assertEquals(4, menu.getNumberEntries());
        assertEquals("Start", menu.getEntry(0));
        assertEquals("Shop", menu.getEntry(1));
        assertEquals("Change User", menu.getEntry(2));
        assertEquals("Exit", menu.getEntry(3));
        assertNotNull(menu.getUserManager());
        if (!menu.getUserManager().getUsers().isEmpty()) {
            assertNotNull(menu.getCurrentUser());
        }
    }

    @Test
    void testNavigation() {
        Menu menu = new Menu();
        int initialMain = 0;
        assertFalse(menu.isUserListActive());
        menu.nextEntry();
        assertFalse(menu.isSelected(initialMain));
        assertTrue(menu.isSelected(initialMain + 1));

        menu.nextEntry();
        menu.nextEntry();
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
        menu.previousEntry();
        assertTrue(menu.isSelected(3));
    }

    @Test
    void testIsSelectedString() {
        Menu menu = new Menu();
        assertTrue(menu.isSelected("Start"));
        assertFalse(menu.isSelected("Exit"));
        menu.previousEntry();
        assertTrue(menu.isSelected("Exit"));
    }

    @Test
    void testUserListToggle() {
        Menu menu = new Menu();
        menu.setUserListActive(true);
        assertTrue(menu.isUserListActive());
        assertEquals(0, menu.getSelectedUserIndex());

        menu.setUserListActive(false);
        assertFalse(menu.isUserListActive());
    }

    @Test
    void testUserListNavigation() {
        Menu menu = new Menu();
        menu.setUserListActive(true);
        int userCount = menu.getUserManager().getUsers().size();
        assertEquals(0, menu.getSelectedUserIndex());
        menu.nextEntry();
        assertEquals(1, menu.getSelectedUserIndex());

        menu.setUserListActive(true);
        menu.previousEntry();
        assertEquals(userCount + 1, menu.getSelectedUserIndex());

        menu.nextEntry();
        assertEquals(0, menu.getSelectedUserIndex());
    }

    @Test
    void testUserManagement() {
        Menu menu = new Menu();
        User u = new User("TestUser", 10, 100, "skin", new java.util.ArrayList<>());
        menu.setCurrentUser(u);
        assertEquals(u, menu.getCurrentUser());
    }
}
