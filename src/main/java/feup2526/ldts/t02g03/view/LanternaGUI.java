package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanternaGUI implements GUI {
    private final Screen screen;
    private final TextGraphics tg;
    private final Map<String, TextColor> colorCache = new HashMap<>();

    public LanternaGUI(Screen screen) {
        this.screen = screen;
        this.tg = screen.newTextGraphics();
    }

    private TextColor getCachedColor(String color) {
        return colorCache.computeIfAbsent(color, TextColor.Factory::fromString);
    }

    @Override
    public GUIImage createOffScreenImage(int width, int height) {
        return new LanternaGUIImage(width, height);
    }

    @Override
    public void drawImage(int x, int y, GUIImage image) {
        if (image instanceof LanternaGUIImage) {
            LanternaGUIImage lanternaImage = (LanternaGUIImage) image;
            TextImage textImage = lanternaImage.getTextImage();
            if (!lanternaImage.hasTransparency()) {
                tg.drawImage(new TerminalPosition(x, y), textImage);
            } else {
                TerminalSize size = textImage.getSize();
                for (int col = 0; col < size.getColumns(); col++) {
                    for (int row = 0; row < size.getRows(); row++) {
                        TextCharacter tc = textImage.getCharacterAt(col, row);
                        if (tc.getBackgroundColor() == null || tc.getBackgroundColor() == TextColor.ANSI.DEFAULT) {
                            continue;
                        }
                        tg.setCharacter(x + col, y + row, tc);
                    }
                }
            }
        }
    }

    public TextGraphics createTextGraphics() {
        return screen.newTextGraphics();
    }

    @Override
    public KeyStroke readInput() throws IOException {
        return screen.pollInput();
    }

    @Override
    public void drawText(int x, int y, String text, String color) {
        tg.setForegroundColor(getCachedColor(color));
        tg.putString(x, y, text);
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        try {
            screen.stopScreen();
        } catch (Exception e) {
            // Ignore if already stopped
        }
        screen.close();
    }

    @Override
    public int getTerminalWidth() {
        return screen.getTerminalSize().getColumns();
    }

    @Override
    public int getTerminalHeight() {
        return screen.getTerminalSize().getRows();
    }
}
