package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;

import java.io.IOException;

public class NewUserViewer extends Viewer<Menu> {
    private static String currentInput = "";

    public NewUserViewer(Menu model) {
        super(model);
    }

    public static void append(char c) {
        currentInput += c;
    }

    public static void backspace() {
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
    }

    public static String getCurrentInput() {
        return currentInput;
    }

    public static void clearInput() {
        currentInput = "";
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        String prompt = "Enter Name:";
        int promptX = (terminalWidth - prompt.length()) / 2;
        int promptY = terminalHeight / 2 - 1;

        gui.drawText(promptX, promptY, prompt, "#FFFFFF");

        int inputX = (terminalWidth - currentInput.length()) / 2;
        int inputY = terminalHeight / 2 + 1;

        gui.drawText(inputX, inputY, currentInput, "#FFD700");
    }
}
