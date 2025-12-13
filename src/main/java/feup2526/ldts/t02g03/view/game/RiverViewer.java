package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.River;
import feup2526.ldts.t02g03.model.game.Log;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class RiverViewer extends SpriteViewer<River> implements LaneViewer<River> {
    private final int width;
    private final LogViewer logViewer;
    private static final String LEFT_SPRITE = "docs/images/sprites/riverLeft.png";
    private static final String RIGHT_SPRITE = "docs/images/sprites/riverRight.png";

    public RiverViewer(int width) {
        super(LEFT_SPRITE);
        this.width = width;
        this.logViewer = new LogViewer();
    }

    @Override
    public void draw(GUI gui, River river, int tileSize, int yPos) {
        String path = (river.getDirection() == Direction.LEFT) ? LEFT_SPRITE : RIGHT_SPRITE;
        GUIImage sprite = getSprite(gui, path);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, yPos);
        }
        drawLogs(gui, river, tileSize, yPos);
    }

    private void drawLogs(GUI gui, River river, int tileSize, int yPos) {
        for (Log l : river.getLogs()) {
            logViewer.draw(gui, l, tileSize, yPos);
        }
    }
}
