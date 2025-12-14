package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;
import java.io.IOException;
import java.util.List;

public class RemoveUserViewer extends Viewer<RemoveUser> {
    public RemoveUserViewer(RemoveUser model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        drawTitle(gui, terminalWidth, terminalHeight);

        if (getModel().isConfirming()) {
            drawConfirmationDialog(gui, terminalWidth, terminalHeight);
        } else {
            drawUserList(gui, terminalWidth, terminalHeight);
        }
    }

    private void drawTitle(GUI gui, int terminalWidth, int terminalHeight) {
        String title = "CROSSY ROADS";
        int titleX = (terminalWidth - title.length()) / 2;
        int titleY = terminalHeight / 4;
        gui.drawText(titleX, titleY, title, "#FFD700");
    }

    private void drawUserList(GUI gui, int terminalWidth, int terminalHeight) {
        String headerText = "Select user to remove:";
        int headerX = (terminalWidth - headerText.length()) / 2;
        int headerY = terminalHeight / 2 - 2;
        gui.drawText(headerX, headerY, headerText, "#FFFFFF");

        List<User> users = getModel().getUsers();
        int listY = terminalHeight / 2;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String displayText = user.toString();
            int nameX = (terminalWidth - displayText.length()) / 2;
            String color = (i == getModel().getSelectedIndex()) ? "#FFD700" : "#FFFFFF";
            gui.drawText(nameX, listY + i, displayText, color);
        }
    }

    private void drawConfirmationDialog(GUI gui, int terminalWidth, int terminalHeight) {
        User userToRemove = getModel().getUserToRemove();
        String confirmText = "Remove " + userToRemove.getName() + "?";
        int confirmX = (terminalWidth - confirmText.length()) / 2;
        int confirmY = terminalHeight / 2;
        gui.drawText(confirmX, confirmY, confirmText, "#FFFFFF");

        int optionsY = confirmY + 2;
        int yesX = (terminalWidth / 2) - 6;
        int noX = (terminalWidth / 2) + 3;
        String yesColor = (getModel().getConfirmOptionIndex() == 0) ? "#FFD700" : "#FFFFFF";
        String noColor = (getModel().getConfirmOptionIndex() == 1) ? "#FFD700" : "#FFFFFF";
        gui.drawText(yesX, optionsY, "Yes", yesColor);
        gui.drawText(noX, optionsY, "No", noColor);
    }
}
