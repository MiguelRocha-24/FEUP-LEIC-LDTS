package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.TerminalSize;

public class LanternaGUIImage implements GUIImage {
    private final TextImage textImage;
    private boolean hasTransparency = false;

    public LanternaGUIImage(int width, int height) {
        this.textImage = new BasicTextImage(new TerminalSize(width, height));
        TextCharacter transparent = TextCharacter.fromCharacter(' ', TextColor.ANSI.DEFAULT, null)[0];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.textImage.setCharacterAt(x, y, transparent);
            }
        }
    }

    public TextImage getTextImage() {
        return textImage;
    }

    @Override
    public int getWidth() {
        return textImage.getSize().getColumns();
    }

    @Override
    public int getHeight() {
        return textImage.getSize().getRows();
    }

    @Override
    public void setPixel(int x, int y, String color) {
        TextColor c = TextColor.Factory.fromString(color);
        TextCharacter tc = TextCharacter.fromCharacter(' ', c, c)[0];
        textImage.setCharacterAt(x, y, tc);
    }

    @Override
    public boolean hasTransparency() {
        return hasTransparency;
    }

    @Override
    public void setTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }
}
