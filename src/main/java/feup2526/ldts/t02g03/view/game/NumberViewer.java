package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NumberViewer {
    private final Map<Integer, GUIImage> numberSprites;

    public NumberViewer() {
        this.numberSprites = new HashMap<>();
    }

    protected GUIImage getSprite(GUI gui, int number) {
        if (!numberSprites.containsKey(number)) {
            try {
                BufferedImage image = ImageIO.read(new File("docs/images/sprites/numbers/nr" + number + ".png"));
                GUIImage guiImage = convertToGUIImage(gui, image);
                numberSprites.put(number, guiImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numberSprites.get(number);
    }

    private GUIImage convertToGUIImage(GUI gui, BufferedImage sprite) {
        return SpriteViewer.convertToGUIImage(gui, sprite);
    }

    public int getWidth(GUI gui, int number) {
        String numberStr = String.valueOf(number);
        int width = 0;
        for (char digitChar : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            GUIImage sprite = getSprite(gui, digit);
            if (sprite != null) {
                width += sprite.getWidth();
            }
        }
        return width;
    }

    public void draw(GUI gui, int number, int x, int y) {
        String numberStr = String.valueOf(number);
        int currentX = x;

        for (char digitChar : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            GUIImage sprite = getSprite(gui, digit);
            if (sprite != null) {
                gui.drawImage(currentX, y, sprite);
                currentX += sprite.getWidth();
            }
        }
    }
}
