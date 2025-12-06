package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.River;
import feup2526.ldts.t02g03.model.Direction;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RiverViewer extends SpriteViewer<River> {
    private final int width;
    private BufferedImage riverLeft;
    private BufferedImage riverRight;

    public RiverViewer(int width) {
        super("docs/images/sprites/riverLeft.png");
        this.width = width;
        try {
            riverLeft = ImageIO.read(new File("docs/images/sprites/riverLeft.png"));
            riverRight = ImageIO.read(new File("docs/images/sprites/riverRight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(TextGraphics tg, River river, int tileSize) {
        BufferedImage spriteToDraw = sprite;
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
