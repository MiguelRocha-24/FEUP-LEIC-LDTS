package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.GameOver;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;

import java.io.IOException;

public class GameOverViewer extends Viewer<GameOver> {
    public GameOverViewer(GameOver model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        String title = "GAME OVER";
        int titleX = (terminalWidth - title.length()) / 2;
        int titleY = terminalHeight / 4;
        gui.drawText(titleX, titleY, title, "#FF0000");
        int statsY = terminalHeight / 2;
        String scoreText = "Score: " + getModel().getScore();
        int scoreX = (terminalWidth - scoreText.length()) / 2;
        gui.drawText(scoreX, statsY, scoreText, "#FFFFFF");

        String highScoreText = "High Score: " + getModel().getHighScore();
        int highScoreX = (terminalWidth - highScoreText.length()) / 2;
        gui.drawText(highScoreX, statsY + 2, highScoreText, "#FFFFFF");

        String coinsText = "Coins: " + getModel().getCoins();
        int coinsX = (terminalWidth - coinsText.length()) / 2;
        gui.drawText(coinsX, statsY + 4, coinsText, "#FFFFFF");

        int menuY = statsY + 8;
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String entry = getModel().getEntry(i);
            int entryX = (terminalWidth - entry.length()) / 2;
            gui.drawText(entryX, menuY + (i * 2), entry, getModel().isSelected(i) ? "#FFFF00" : "#FFFFFF");
        }
    }
}
