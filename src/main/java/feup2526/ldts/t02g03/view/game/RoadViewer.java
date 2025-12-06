package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.RoadLane;
import com.googlecode.lanterna.graphics.TextGraphics;

public class RoadViewer extends SpriteViewer<RoadLane> {
    private final int width;

    public RoadViewer(int width) {
        super("docs/images/sprites/road.png");
        this.width = width;

    }

    @Override
    public void draw(TextGraphics tg, RoadLane lane, int tileSize) {
        for (int i = 0; i < width; i++) {
            drawSprite(tg, sprite, i * tileSize, lane.getRow() * tileSize);
        }
    }
}
