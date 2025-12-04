package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.Log;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LogViewer extends SpriteViewer<Log> {
    public LogViewer() {
        super("docs/images/sprites/log2.png");
    }

    @Override
    public void draw(TextGraphics tg, Log log, int tileSize) {
        drawSprite(tg, sprite, (int) (log.getPosition().getX() * tileSize),
                (int) (log.getPosition().getY() * tileSize));
    }
}
