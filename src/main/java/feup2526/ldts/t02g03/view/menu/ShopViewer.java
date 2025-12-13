package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.model.menu.Skin;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.game.SpriteViewer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShopViewer extends Viewer<Shop> {
    private final Map<String, GUIImage> spriteCache = new HashMap<>();

    public ShopViewer(Shop shop) {
        super(shop);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        Shop shop = getModel();
        Skin currentSkin = shop.getSelectedSkin();
        User user = shop.getUser();

        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        String skinName = currentSkin.getName().toUpperCase();
        int nameX = (terminalWidth - skinName.length()) / 2;
        int nameY = terminalHeight / 6;
        gui.drawText(nameX, nameY, skinName, "#FFFFFF");

        try {
            GUIImage skinImage = getSprite(gui, currentSkin.getName() + "Right");
            if (skinImage != null) {
                int imgX = (terminalWidth - skinImage.getWidth()) / 2;
                int imgY = nameY + 2;
                gui.drawImage(imgX, imgY, skinImage);
            }
        } catch (Exception e) {
            gui.drawText((terminalWidth - 14) / 2, nameY + 2, "Image not found", "#FF0000");
        }

        boolean isOwned = user.getOwnedSkins().contains(currentSkin.getName());
        boolean isEquipped = user.getEquippedSkin().equals(currentSkin.getName());

        String priceText;
        String priceColor;

        if (isOwned) {
            priceText = "UNLOCKED";
            priceColor = "#00FF00"; // Green
        } else {
            priceText = currentSkin.getPrice() + " COINS";
            if (user.getCoins() >= currentSkin.getPrice()) {
                priceColor = "#00FF00";
            } else {
                priceColor = "#FF0000"; // Red if can't afford
            }
        }

        int priceX = (terminalWidth - priceText.length()) / 2;
        int priceY = terminalHeight / 2 + 2;
        gui.drawText(priceX, priceY, priceText, priceColor);

        String buttonText;
        String buttonColor;

        if (isEquipped) {
            buttonText = "SELECTED";
            buttonColor = "#FFD700"; // Gold
        } else if (isOwned) {
            buttonText = "SELECT";
            buttonColor = "#FFFFFF";
        } else {
            buttonText = "UNLOCK";
            buttonColor = "#FFFFFF"; 
        }

        int btnX = (terminalWidth - buttonText.length()) / 2;
        int btnY = priceY + 2;

        gui.drawText(btnX, btnY, buttonText, buttonColor);

        String returnText = "RETURN (ESC)";
        int returnX = (terminalWidth - returnText.length()) / 2;
        int returnY = btnY + 2;
        gui.drawText(returnX, returnY, returnText, "#FFFFFF");

        String coinsInfo = "COINS: " + user.getCoins();
        gui.drawText(1, 1, coinsInfo, "#FFD700");
    }

    private GUIImage getSprite(GUI gui, String skinName) {
        if (spriteCache.containsKey(skinName)) {
            return spriteCache.get(skinName);
        }

        try {
            String path = "docs/images/sprites/" + skinName + ".png";
            File file = new File(path);
            if (!file.exists()) return null;

            BufferedImage originalImage = ImageIO.read(file);
            GUIImage image = SpriteViewer.convertToGUIImage(gui, originalImage);
            spriteCache.put(skinName, image);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
