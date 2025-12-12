package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public interface GUI {
    GUIImage createOffScreenImage(int width, int height);

    void drawImage(int x, int y, GUIImage image);

    KeyStroke readInput() throws IOException;

    void drawText(int x, int y, String text, String color);

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    int getTerminalWidth();

    int getTerminalHeight();
}
