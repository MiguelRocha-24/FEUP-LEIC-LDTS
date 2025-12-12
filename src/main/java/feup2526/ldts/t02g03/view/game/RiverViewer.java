package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.River;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class RiverViewer extends SpriteViewer<River> {
    private final int width;
    private static final String LEFT_SPRITE = "docs/images/sprites/riverLeft.png";
    private static final String RIGHT_SPRITE = "docs/images/sprites/riverRight.png";

    public RiverViewer(int width) {
        super(LEFT_SPRITE);
        this.width = width;
    }

    @Override
    public void draw(GUI gui, River river, int tileSize) {
        String path = (river.getDirection() == Direction.LEFT) ? LEFT_SPRITE : RIGHT_SPRITE;
        GUIImage sprite = getSprite(gui, path);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, river.getRow() * tileSize);
        }
    }
}
