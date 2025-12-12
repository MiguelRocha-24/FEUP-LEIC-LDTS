package feup2526.ldts.t02g03.model.menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private final String filePath = "docs/Shop/skins.csv";
    private List<Skin> skins;

    public Shop() {
        this.skins = new ArrayList<>();
        loadSkins();
    }

    private void loadSkins() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    skins.add(new Skin(name, count));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Skin> getSkins() {
        return skins;
    }

    public Skin getSkins(String name) {
        for (Skin skin : skins) {
            if (skin.getName().equals(name)) {
                return skin;
            }
        }
        return null;
    }
}

