package feup2526.ldts.t02g03.model.menu;

public class User {
    private String name;
    private int coins;
    private int highScore;

    public User(String name, int coins, int highScore) {
        this.name = name;
        this.coins = coins;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return String.format("%s (C:%d HS:%d)", name, coins, highScore);
    }
}
