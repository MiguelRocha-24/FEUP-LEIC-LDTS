package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SpriteViewer<T> implements ElementViewer<T> {
    protected BufferedImage sprite;

    public SpriteViewer(String spritePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(spritePath));
            this.sprite = originalImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void drawSprite(TextGraphics graphics, BufferedImage sprite, int xPos, int yPos) {
        if (sprite == null)
            return;

        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                int a = sprite.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha != 0) {
                    TextCharacter c = TextCharacter.fromCharacter(' ',
                            new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue))[0];
                    graphics.setCharacter(xPos + x, yPos + y, c);
                }
            }
        }
    }
}
