package feup2526.ldts.t02g03.model.menu;

import java.util.Arrays;
import java.util.List;

public class GameOver {
    private final List<String> entries;
    private int currentEntry = 0;
    private final int score;
    private final int highScore;
    private final int coins;

    public GameOver(int score, int highScore, int coins) {
        this.entries = Arrays.asList("Play Again", "Main Menu");
        this.score = score;
        this.highScore = highScore;
        this.coins = coins;
    }

    public void nextEntry() {
        currentEntry++;
        if (currentEntry > entries.size() - 1)
            currentEntry = 0;
    }

    public void previousEntry() {
        currentEntry--;
        if (currentEntry < 0)
            currentEntry = entries.size() - 1;
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

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCoins() {
        return coins;
    }
}
