package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.River;
import feup2526.ldts.t02g03.model.Direction;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextCharacter;


public class RiverViewer extends SpriteViewer<River> {
    private final int width;
    private TextCharacter[][] riverLeft;
    private TextCharacter[][] riverRight;

    public RiverViewer(int width) {
        super("docs/images/sprites/riverLeft.png");
        this.width = width;
        riverLeft = loadSprite("docs/images/sprites/riverLeft.png");
        riverRight = loadSprite("docs/images/sprites/riverRight.png");
    }

    @Override
    public void draw(TextGraphics tg, River river, int tileSize) {
        TextCharacter[][] spriteToDraw = sprite;
        if (river.getDirection() == Direction.LEFT && riverLeft != null) {
            spriteToDraw = riverLeft;
        } else if (river.getDirection() == Direction.RIGHT && riverRight != null) {
            spriteToDraw = riverRight;
        }
        for (int i = 0; i < width; i++) {
            drawSprite(tg, spriteToDraw, i * tileSize, river.getRow() * tileSize);
        }
    }
}
