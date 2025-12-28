package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class RemoveUserTest {
    @Test
    void testRemoveUserLogic() {
        Menu mockMenu = Mockito.mock(Menu.class);
        UserManager mockUM = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUM);

        RemoveUser ru = new RemoveUser(mockMenu);
        assertNotNull(ru.getParentMenu());

        ru.setConfirming(true);
        assertTrue(ru.isConfirming());
        assertEquals(1, ru.getConfirmOptionIndex());

        ru.toggleConfirmOption();
        assertEquals(0, ru.getConfirmOptionIndex());
    }

    @Test
    void testNavigationWithUsers() {
        Menu mockMenu = Mockito.mock(Menu.class);
        UserManager mockUM = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUM);

        List<User> users = Arrays.asList(
                new User("User1", 0, 0, "skin", new ArrayList<>()),
                new User("User2", 0, 0, "skin", new ArrayList<>()),
                new User("User3", 0, 0, "skin", new ArrayList<>()));
        Mockito.when(mockUM.getUsers()).thenReturn(users);
        RemoveUser ru = new RemoveUser(mockMenu);

        assertEquals(0, ru.getSelectedIndex());
        assertEquals("User1", ru.getSelectedUser().getName());
        ru.nextEntry();
        assertEquals(1, ru.getSelectedIndex());
        assertEquals("User2", ru.getSelectedUser().getName());
        ru.nextEntry();
        assertEquals(2, ru.getSelectedIndex());
        assertEquals("User3", ru.getSelectedUser().getName());
        ru.nextEntry();
        assertEquals(0, ru.getSelectedIndex());
        assertEquals("User1", ru.getSelectedUser().getName());
        ru.previousEntry();
        assertEquals(2, ru.getSelectedIndex());
        assertEquals("User3", ru.getSelectedUser().getName());
        ru.previousEntry();
        assertEquals(1, ru.getSelectedIndex());
    }

    @Test
    void testNavigationEmpty() {
        Menu mockMenu = Mockito.mock(Menu.class);
        UserManager mockUM = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUM);
        Mockito.when(mockUM.getUsers()).thenReturn(Collections.emptyList());

        RemoveUser ru = new RemoveUser(mockMenu);
        assertEquals(0, ru.getSelectedIndex());
        assertNull(ru.getSelectedUser());
        ru.nextEntry();
        assertEquals(0, ru.getSelectedIndex());
        ru.previousEntry();
        assertEquals(0, ru.getSelectedIndex());
    }

    @Test
    void testConfirmationSet() {
        Menu mockMenu = Mockito.mock(Menu.class);
        UserManager mockUM = Mockito.mock(UserManager.class);
        Mockito.when(mockMenu.getUserManager()).thenReturn(mockUM);

        User user1 = new User("User1", 0, 0, "skin", new ArrayList<>());
        List<User> users = Collections.singletonList(user1);
        Mockito.when(mockUM.getUsers()).thenReturn(users);

        RemoveUser ru = new RemoveUser(mockMenu);

        assertEquals(user1, ru.getSelectedUser());
        ru.setConfirming(true);
        assertTrue(ru.isConfirming());
        assertEquals(user1, ru.getUserToRemove());
        ru.setConfirming(false);
        assertFalse(ru.isConfirming());
    }
}
