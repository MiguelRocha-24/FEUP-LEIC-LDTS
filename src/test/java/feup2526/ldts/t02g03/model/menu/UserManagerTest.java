package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;
    private File tempFile;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Create a temporary file for each test
        tempFile = tempDir.resolve("test_users.csv").toFile();
        
        // Initialize UserManager
        userManager = new UserManager();

        // Use reflection to change the filePath field to our temp file
        Field filePathField = UserManager.class.getDeclaredField("filePath");
        filePathField.setAccessible(true);
        filePathField.set(userManager, tempFile.getAbsolutePath());
        
        // Clear the users list in the userManager instance to start fresh (since constructor loads from default path)
        Field usersField = UserManager.class.getDeclaredField("users");
        usersField.setAccessible(true);
        ((List<?>) usersField.get(userManager)).clear();
    }

    @Test
    void testAddUser() {
        userManager.addUser("TestUser");
        
        assertEquals(1, userManager.getUsers().size());
        assertEquals("TestUser", userManager.getUsers().get(0).getName());
        assertEquals(0, userManager.getUsers().get(0).getCoins());
    }

    @Test
    void testAddDuplicateUser() {
        userManager.addUser("TestUser");
        userManager.addUser("TestUser"); // Should be ignored
        
        assertEquals(1, userManager.getUsers().size());
    }

    @Test
    void testRemoveUser() {
        userManager.addUser("UserToRemove");
        userManager.removeUser("UserToRemove");
        
        assertEquals(0, userManager.getUsers().size());
    }

    @Test
    void testUpdateUser() {
        userManager.addUser("UserToUpdate");
        User user = userManager.getUser("UserToUpdate");
        
        User updatedUser = new User("UserToUpdate", 100, 50, "chicken", new ArrayList<>(Arrays.asList("chicken", "fox")));
        userManager.updateUser(updatedUser);
        
        User retrievedUser = userManager.getUser("UserToUpdate");
        assertEquals(100, retrievedUser.getCoins());
        assertEquals(50, retrievedUser.getHighScore());
        assertEquals(2, retrievedUser.getOwnedSkins().size());
    }

    @Test
    void testSaveAndLoadUsers() throws NoSuchFieldException, IllegalAccessException {
        // Add a user and save
        userManager.addUser("PersistentUser");
        User u = userManager.getUser("PersistentUser");
        u.setCoins(99);
        userManager.updateUser(u); // calls saveUsers internally

        // Create a new UserManager instance pointing to the same file
        UserManager newManager = new UserManager();
        
        // Reflection magic again for the new instance
        Field filePathField = UserManager.class.getDeclaredField("filePath");
        filePathField.setAccessible(true);
        filePathField.set(newManager, tempFile.getAbsolutePath());
        
        // Manually trigger load since constructor ran before we swapped the path
        java.lang.reflect.Method loadUsersMethod;
        try {
            loadUsersMethod = UserManager.class.getDeclaredMethod("loadUsers");
            loadUsersMethod.setAccessible(true);
            
            // Clear default users loaded from real file
             Field usersField = UserManager.class.getDeclaredField("users");
             usersField.setAccessible(true);
             ((List<?>) usersField.get(newManager)).clear();
             
             // Load from our temp file
            loadUsersMethod.invoke(newManager);
        } catch (Exception e) {
            fail("Failed to invoke loadUsers: " + e.getMessage());
        }

        assertEquals(1, newManager.getUsers().size(), "Should have loaded 1 user");
        assertEquals("PersistentUser", newManager.getUsers().get(0).getName());
        assertEquals(99, newManager.getUsers().get(0).getCoins());
    }
}
