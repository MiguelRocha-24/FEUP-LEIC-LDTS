package feup2526.ldts.t02g03.model.menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final String filePath = "docs/users.csv";
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
        loadUsers();
        if (users.isEmpty()) {
            addUser("Guest");
        }
    }

    private void loadUsers() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?![^\\[]*\\])"); // Split by comma, but not inside brackets
                if (parts.length == 5) {
                    String name = parts[0].trim();
                    int coins = Integer.parseInt(parts[1].trim());
                    int highScore = Integer.parseInt(parts[2].trim());
                    String equippedSkin = parts[3].trim();
                    String skinsString = parts[4].trim();
                    ArrayList<String> ownedSkins = new ArrayList<>();
                    if (skinsString.startsWith("[") && skinsString.endsWith("]")) {
                        String skinsContent = skinsString.substring(1, skinsString.length() - 1);
                        if (!skinsContent.isEmpty()) {
                            String[] skinArray = skinsContent.split(",");
                            for (String skin : skinArray) {
                                ownedSkins.add(skin.trim());
                            }
                        }
                    }

                    users.add(new User(name, coins, highScore, equippedSkin, ownedSkins));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                String skinsFormatted = "[" + String.join(",", user.getOwnedSkins()) + "]";
                bw.write(user.getName() + "," + user.getCoins() + "," + user.getHighScore() + "," + user.getEquippedSkin() + "," + skinsFormatted);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(String name) {
        // Check if user already exists
        for (User user : users) {
            if (user.getName().equals(name)) {
                return;
            }
        }
        ArrayList<String> defaultSkins = new ArrayList<>();
        defaultSkins.add("chicken");
        users.add(new User(name, 0, 0,"chicken",defaultSkins));
        saveUsers();
    }

    public void updateUser(User updatedUser) {
        for (User user : users) {
            if (user.getName().equals(updatedUser.getName())) {
                user.setCoins(updatedUser.getCoins());
                user.setHighScore(updatedUser.getHighScore());
                user.setEquippedSkin(updatedUser.getEquippedSkin());
                user.setOwnedSkins(updatedUser.getOwnedSkins());
                saveUsers();
                return;
            }
        }
    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
