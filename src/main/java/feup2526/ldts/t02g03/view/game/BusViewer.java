package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Bus;
import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class BusViewer extends SpriteViewer<Bus> {
    private static final String LEFT_SPRITE = "docs/images/sprites/Bus-Left.png";
    private static final String RIGHT_SPRITE = "docs/images/sprites/Bus-Right.png";

    public BusViewer() {
        super(LEFT_SPRITE);
    }

    @Override
    public void draw(GUI gui, Bus bus, int tileSize, int yPos) {
        String path = (bus.getDirection() == Direction.LEFT) ? LEFT_SPRITE : RIGHT_SPRITE;
        GUIImage sprite = getSprite(gui, path);
        drawSprite(gui, sprite, (int) (bus.getPosition().getX() * tileSize), yPos );
    }
}
