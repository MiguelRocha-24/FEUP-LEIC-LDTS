package feup2526.ldts.t02g03.view.game;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NumberViewer {
    private final Map<Integer, TextCharacter[][]> numberSprites;

    public NumberViewer() {
        this.numberSprites = new HashMap<>();
        loadSprites();
    }

    private void loadSprites() {
        for (int i = 0; i <= 9; i++) {
            try {
                BufferedImage image = ImageIO.read(new File("docs/images/sprites/numbers/nr" + i + ".png"));
                numberSprites.put(i, convertToTextCharacters(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    public int getWidth(int number) {
        String numberStr = String.valueOf(number);
        int width = 0;
        for (char digitChar : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            TextCharacter[][] sprite = numberSprites.get(digit);
            if (sprite != null) {
                width += sprite.length;
            }
        }
        return width;
    }

    public void draw(TextGraphics tg, int number, int x, int y) {
        String numberStr = String.valueOf(number);
        int currentX = x;

        for (char digitChar : numberStr.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            TextCharacter[][] sprite = numberSprites.get(digit);
            if (sprite != null) {
                drawSprite(tg, sprite, currentX, y);
                currentX += sprite.length;
            }
        }
    }

    private void drawSprite(TextGraphics graphics, TextCharacter[][] sprite, int xPos, int yPos) {
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
