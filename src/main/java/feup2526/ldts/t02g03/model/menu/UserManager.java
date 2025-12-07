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
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int coins = Integer.parseInt(parts[1]);
                    int highScore = Integer.parseInt(parts[2]);
                    users.add(new User(name, coins, highScore));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(user.getName() + "," + user.getCoins() + "," + user.getHighScore());
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
        users.add(new User(name, 0, 0));
        saveUsers();
    }

    public void updateUser(User updatedUser) {
        for (User user : users) {
            if (user.getName().equals(updatedUser.getName())) {
                user.setCoins(updatedUser.getCoins());
                user.setHighScore(updatedUser.getHighScore());
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
