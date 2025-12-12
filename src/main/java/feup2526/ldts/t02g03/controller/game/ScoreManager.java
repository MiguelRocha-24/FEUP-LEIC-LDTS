package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.HighestScore;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.RunScore;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;

public class ScoreManager {
    private final Level level;
    private final RunScore runScore;
    private final HighestScore highestScore;
    private final UserManager userManager;
    private int minRowReached;

    public ScoreManager(Level level, UserManager userManager) {
        this.level = level;
        this.userManager = userManager;
        this.runScore = level.getRunScore();
        this.highestScore = new HighestScore();
        this.minRowReached = (int) level.getPlayer().getPosition().getY();
    }

    public void updateScore() {
        int currentRow = (int) Math.round(level.getPlayer().getPosition().getY());
        if (currentRow < minRowReached) {
            runScore.increment(minRowReached - currentRow);
            minRowReached = currentRow;
        }
    }

    public void updateUserStats(feup2526.ldts.t02g03.application.Game game) {
        User user = game.getCurrentUser();
        if (user != null) {
            int coins = level.getCoinCounter().getCount();
            int score = level.getRunScore().getCount();

            user.setCoins(user.getCoins() + coins);
            if (score > user.getHighScore()) {
                user.setHighScore(score);
            }

            userManager.updateUser(user);
        }
    }

    public RunScore getRunScore() {
        return runScore;
    }

    public HighestScore getHighestScore() {
        return highestScore;
    }
}
