package feup2526.ldts.t02g03.model.menu;

import java.util.List;

public class RemoveUser {
    private final Menu parentMenu;
    private int selectedIndex = 0;
    private boolean confirming = false;
    private int confirmOptionIndex = 1;
    private User userToRemove = null;

    public RemoveUser(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public UserManager getUserManager() {
        return parentMenu.getUserManager();
    }

    public List<User> getUsers() {
        return getUserManager().getUsers();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void nextEntry() {
        int size = getUsers().size();
        if (size > 0) {
            selectedIndex = (selectedIndex + 1) % size;
        }
    }

    public void previousEntry() {
        int size = getUsers().size();
        if (size > 0) {
            selectedIndex = (selectedIndex - 1 + size) % size;
        }
    }

    public User getSelectedUser() {
        if (selectedIndex < getUsers().size()) {
            return getUsers().get(selectedIndex);
        }
        return null;
    }

    public boolean isConfirming() {
        return confirming;
    }

    public void setConfirming(boolean confirming) {
        this.confirming = confirming;
        this.confirmOptionIndex = 1;
        if (confirming) {
            this.userToRemove = getSelectedUser();
        }
    }

    public User getUserToRemove() {
        return userToRemove;
    }

    public int getConfirmOptionIndex() {
        return confirmOptionIndex;
    }

    public void toggleConfirmOption() {
        confirmOptionIndex = confirmOptionIndex == 0 ? 1 : 0;
    }
}
