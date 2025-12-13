package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.view.GUI;

public interface LaneViewer<T extends Lane> {
    void draw(GUI gui, T lane, int tileSize, int drawY);
}
