package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Log;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class LogViewer extends SpriteViewer<Log> {
    public LogViewer() {
        super("docs/images/sprites/log.png");
    }

    @Override
    public void draw(GUI gui, Log log, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        drawSprite(gui, sprite, (int) (log.getPosition().getX() * tileSize), yPos + 3);
    }
}
