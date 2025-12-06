package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.SafeLane;
import com.googlecode.lanterna.graphics.TextGraphics;

public class SafeLaneViewer extends SpriteViewer<SafeLane> {
    private final int width;

    public SafeLaneViewer(int width) {
        super("docs/images/sprites/grass2.png");
        this.width = width;

    }

    @Override
    public void draw(TextGraphics tg, SafeLane lane, int tileSize) {
        for (int i = 0; i < width; i++) {
            drawSprite(tg, sprite, i * tileSize, lane.getRow() * tileSize);
        }
    }
}

