package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;

public interface ElementViewer<T> {
    void draw(GUI gui, T element, int tileSize, int yPos);
}
