package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;
import java.io.IOException;
import java.util.List;

public class MenuViewer extends Viewer<Menu> {
    public MenuViewer(Menu menu) {
        super(menu);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        String title = "CROSSY ROADS";
        int titleX = (terminalWidth - title.length()) / 2;
        int titleY = terminalHeight / 4;

        gui.drawText(titleX, titleY, title, "#FFD700");

        // Draw Current User
        User currentUser = getModel().getCurrentUser();
        if (currentUser != null) {
            String userText = "User: " + currentUser.getName();
            gui.drawText(terminalWidth - userText.length() - 2, 1, userText, "#FFFFFF");

            String coinsText = "Coins: " + currentUser.getCoins();
            gui.drawText(terminalWidth - coinsText.length() - 2, 2, coinsText, "#FFFFFF");

            String scoreText = "High Score: " + currentUser.getHighScore();
            gui.drawText(terminalWidth - scoreText.length() - 2, 3, scoreText, "#FFFFFF");
        }

        if (getModel().isUserListActive()) {
            List<User> users = getModel().getUserManager().getUsers();
            int listY = terminalHeight / 2;

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                String displayText = user.toString();
                int nameX = (terminalWidth - displayText.length()) / 2;
                String color = (i == getModel().getSelectedUserIndex()) ? "#FFD700" : "#FFFFFF";
                gui.drawText(nameX, listY + i, displayText, color);
            }

            // New User option
            String newUserText = "New User";
            int newUserX = (terminalWidth - newUserText.length()) / 2;
            String color = (users.size() == getModel().getSelectedUserIndex()) ? "#FFD700" : "#FFFFFF";
            gui.drawText(newUserX, listY + users.size(), newUserText, color);
        } else {
            int startY = terminalHeight / 2;
            for (int i = 0; i < getModel().getNumberEntries(); i++) {
                String entry = getModel().getEntry(i);
                int entryX = (terminalWidth - entry.length()) / 2;
                gui.drawText(
                        entryX, startY + (i * 3),
                        entry,
                        getModel().isSelected(i) ? "#FFD700" : "#FFFFFF");
            }
        }
    }
}
