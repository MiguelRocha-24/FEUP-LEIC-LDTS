package feup2526.ldts.t02g03.model.menu;

import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    private int currentEntry = 0;
    private UserManager userManager;
    private boolean isUserListActive = false;
    private int selectedUserIndex = 0;
    private User currentUser;

    public Menu() {
        this.entries = Arrays.asList("Start", "Shop", "Change User", "Exit");
        this.userManager = new UserManager();
        if (!userManager.getUsers().isEmpty()) {
            this.currentUser = userManager.getUsers().get(0);
        }
    }

    public void nextEntry() {
        if (isUserListActive) {
            selectedUserIndex++;
            // +2 for "New User" and "Remove User" options
            if (selectedUserIndex > userManager.getUsers().size() + 1)
                selectedUserIndex = 0;
        } else {
            currentEntry++;
            if (currentEntry > entries.size() - 1)
                currentEntry = 0;
        }
    }

    public void previousEntry() {
        if (isUserListActive) {
            selectedUserIndex--;
            if (selectedUserIndex < 0)
                selectedUserIndex = userManager.getUsers().size() + 1;
        } else {
            currentEntry--;
            if (currentEntry < 0)
                currentEntry = entries.size() - 1;
        }
    }

    public String getEntry(int i) {
        return entries.get(i);
    }

    public boolean isSelected(int i) {
        return currentEntry == i;
    }

    public boolean isSelected(String entry) {
        return entries.get(currentEntry).equals(entry);
    }

    public int getNumberEntries() {
        return entries.size();
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public boolean isUserListActive() {
        return isUserListActive;
    }

    public void setUserListActive(boolean userListActive) {
        isUserListActive = userListActive;
        selectedUserIndex = 0;
    }

    public int getSelectedUserIndex() {
        return selectedUserIndex;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
