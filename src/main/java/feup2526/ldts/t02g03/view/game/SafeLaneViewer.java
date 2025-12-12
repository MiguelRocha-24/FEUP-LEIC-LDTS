package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.SafeLane;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class SafeLaneViewer extends SpriteViewer<SafeLane> {
    private final int width;

    public SafeLaneViewer(int width) {
        super("docs/images/sprites/grass2.png");
        this.width = width;
    }

    @Override
    public void draw(GUI gui, SafeLane lane, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, yPos);
        }
    }
}
