package feup2526.ldts.t02g03.model.menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Skin> skins;
    private User user;

    public Shop(User user) {
        this.skins = new ArrayList<Skin>();
        skins.add(new Skin("chicken",0));
        skins.add(new Skin("frog",10));
        skins.add(new Skin("dog",50));
        skins.add(new Skin("rabbit",100));
        this.user = user;
    }

    public List<Skin> getSkins() {
        return skins;
    }

    public Skin getSkin(String name) {
        for (Skin skin : skins) {
            if (skin.getName().equals(name)) {
                return skin;
            }
        }
        return null;
    }

    private int selectedSkinIndex = 0;

    public void nextSkin() {
        selectedSkinIndex++;
        if (selectedSkinIndex >= skins.size()) {
            selectedSkinIndex = 0;
        }
    }

    public void previousSkin() {
        selectedSkinIndex--;
        if (selectedSkinIndex < 0) {
            selectedSkinIndex = skins.size() - 1;
        }
    }

    public Skin getSelectedSkin() {
        return skins.get(selectedSkinIndex);
    }

    public int getSelectedSkinIndex() {
        return selectedSkinIndex;
    }

    public boolean isSelected(int i) {
        return selectedSkinIndex == i;
    }

    public User getUser() {
        return user;
    }

}

