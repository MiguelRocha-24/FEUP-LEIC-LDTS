package feup2526.ldts.t02g03.view.game;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SpriteViewer<T> implements ElementViewer<T> {
    protected TextCharacter[][] sprite;
    private static final java.util.Map<String, TextCharacter[][]> cache = new java.util.HashMap<>();

    public SpriteViewer(String spritePath) {
        this.sprite = loadSprite(spritePath);
    }

    protected TextCharacter[][] loadSprite(String spritePath) {
        if (cache.containsKey(spritePath)) {
            return cache.get(spritePath);
        }
        try {
            BufferedImage originalImage = ImageIO.read(new File(spritePath));
            TextCharacter[][] textChars = convertToTextCharacters(originalImage);
            cache.put(spritePath, textChars);
            return textChars;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Implmented differently to professor, 
    //Allows for a more efficient loading of sprites, by only loading the sprite once and storing its drawing 
    private TextCharacter[][] convertToTextCharacters(BufferedImage sprite) {
        int width = sprite.getWidth();
        int height = sprite.getHeight();
        TextCharacter[][] characters = new TextCharacter[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int a = sprite.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha != 0) {
                    characters[x][y] = TextCharacter.fromCharacter(' ',
                            new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue))[0];
                } else {
                    characters[x][y] = null;
                }
            }
        }
        return characters;
    }

    protected void drawSprite(TextGraphics graphics, TextCharacter[][] sprite, int xPos, int yPos) {
        if (sprite == null)
            return;

        for (int x = 0; x < sprite.length; x++) {
            for (int y = 0; y < sprite[0].length; y++) {
                TextCharacter c = sprite[x][y];
                if (c != null) {
                    graphics.setCharacter(xPos + x, yPos + y, c);
                }
            }
        }
    }
}
