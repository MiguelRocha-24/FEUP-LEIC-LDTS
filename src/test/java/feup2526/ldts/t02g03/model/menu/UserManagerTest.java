package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;
    private File tempFile;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("test_users.csv").toFile();
        userManager = new UserManager(tempFile.getAbsolutePath());
        userManager.removeUser("Guest");
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
        userManager.addUser("TestUser");
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
        User updatedUser = new User("UserToUpdate", 100, 50, "chicken", new ArrayList<>(Arrays.asList("chicken", "fox")));
        userManager.updateUser(updatedUser);
        User retrievedUser = userManager.getUser("UserToUpdate");
        assertEquals(100, retrievedUser.getCoins());
        assertEquals(50, retrievedUser.getHighScore());
        assertEquals(2, retrievedUser.getOwnedSkins().size());
    }

    @Test
    void testSaveAndLoadUsers() {
        userManager.addUser("PersistentUser");
        User u = userManager.getUser("PersistentUser");
        u.setCoins(99);
        userManager.updateUser(u);
        UserManager newManager = new UserManager(tempFile.getAbsolutePath());
        assertEquals(1, newManager.getUsers().size(), "Should have loaded 1 user");
        assertEquals("PersistentUser", newManager.getUsers().get(0).getName());
        assertEquals(99, newManager.getUsers().get(0).getCoins());
    }
}
