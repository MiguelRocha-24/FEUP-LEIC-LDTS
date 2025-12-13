package feup2526.ldts.t02g03.model.menu;

import java.util.ArrayList;

public class User {
    private String name;
    private int coins;
    private int highScore;
    private String equippedSkin;
    private ArrayList<String> ownedSkins;

    public User(String name, int coins, int highScore, String equippedSkin, ArrayList<String> ownedSkins) {
        this.name = name;
        this.coins = coins;
        this.highScore = highScore;
        this.equippedSkin = equippedSkin;
        this.ownedSkins = ownedSkins;
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

    public String getEquippedSkin() {
        return equippedSkin;
    }

    public void setEquippedSkin(String equippedSkin) {
        this.equippedSkin = equippedSkin;
    }

    public ArrayList<String> getOwnedSkins() {
        return ownedSkins;
    }

    public void setOwnedSkins(ArrayList<String> ownedSkins) {
        this.ownedSkins = ownedSkins;
    }

    @Override
    public String toString() {
        return String.format("%s (C:%d HS:%d)", name, coins, highScore);
    }
}
