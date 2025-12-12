package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SpriteViewer<T> implements ElementViewer<T> {
    private final String spritePath;
    private static final java.util.Map<String, GUIImage> cache = new java.util.HashMap<>();

    public SpriteViewer(String spritePath) {
        this.spritePath = spritePath;
    }

    protected GUIImage getSprite(GUI gui) {
        return getSprite(gui, spritePath);
    }

    protected GUIImage getSprite(GUI gui, String path) {
        if (cache.containsKey(path)) {
            return cache.get(path);
        }
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            GUIImage image = convertToGUIImage(gui, originalImage);
            cache.put(path, image);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GUIImage convertToGUIImage(GUI gui, BufferedImage sprite) {
        int width = sprite.getWidth();
        int height = sprite.getHeight();
        GUIImage image = gui.createOffScreenImage(width, height);
        boolean hasTransparency = false;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int a = sprite.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha != 0) {
                    String color = String.format("#%02x%02x%02x", red, green, blue);
                    image.setPixel(x, y, color);
                } else {
                    hasTransparency = true;
                }
            }
        }
        image.setTransparency(hasTransparency);
        return image;
    }

    protected void drawSprite(GUI gui, GUIImage sprite, int xPos, int yPos) {
        if (sprite != null) {
            gui.drawImage(xPos, yPos, sprite);
        }
    }
}
