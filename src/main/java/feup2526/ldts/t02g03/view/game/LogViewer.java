package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Log;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class LogViewer extends SpriteViewer<Log> {
    public LogViewer() {
        super("docs/images/sprites/log2.png");
    }

    @Override
    public void draw(GUI gui, Log log, int tileSize) {
        GUIImage sprite = getSprite(gui);
        drawSprite(gui, sprite, (int) (log.getPosition().getX() * tileSize),
                (int) (log.getPosition().getY() * tileSize));
    }
}
