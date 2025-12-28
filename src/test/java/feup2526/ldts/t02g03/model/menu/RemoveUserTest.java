package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
}
