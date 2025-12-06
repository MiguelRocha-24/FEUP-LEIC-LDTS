package feup2526.ldts.t02g03.view.game;

import com.googlecode.lanterna.graphics.TextGraphics;

public interface ElementViewer<T> {
    void draw(TextGraphics tg, T element, int tileSize);
}
