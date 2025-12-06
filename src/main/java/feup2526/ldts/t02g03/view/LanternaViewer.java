package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class LanternaViewer {
    private final Screen screen;

    public LanternaViewer(Screen screen) {
        this.screen = screen;
    }

    public TextGraphics createTextGraphics() {
        return screen.newTextGraphics();
    }

    public KeyStroke readInput() throws IOException {
        return screen.pollInput();
    }

    public void drawText(int x, int y, String text, String color) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(com.googlecode.lanterna.TextColor.Factory.fromString(color));
        tg.putString(x, y, text);
    }

    public void clear() {
        screen.clear();
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public void close() throws IOException {
        screen.close();
    }

    public int getTerminalWidth() {
        return screen.getTerminalSize().getColumns();
    }

    public int getTerminalHeight() {
        return screen.getTerminalSize().getRows();
    }
}
