package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.RoadLane;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class RoadViewer extends SpriteViewer<RoadLane> {
    private final int width;

    public RoadViewer(int width) {
        super("docs/images/sprites/road.png");
        this.width = width;
    }

    @Override
    public void draw(GUI gui, RoadLane lane, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, yPos);
        }
    }
}
