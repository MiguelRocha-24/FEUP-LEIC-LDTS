package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Vehicle;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

import feup2526.ldts.t02g03.model.game.Direction;

public class CarViewer extends SpriteViewer<Vehicle> {
    private static final String LEFT_SPRITE = "docs/images/sprites/carLeft.png";
    private static final String RIGHT_SPRITE = "docs/images/sprites/carRight.png";

    public CarViewer() {
        super(LEFT_SPRITE);
    }

    @Override
    public void draw(GUI gui, Vehicle vehicle, int tileSize) {
        String path = (vehicle.getDirection() == Direction.LEFT) ? LEFT_SPRITE : RIGHT_SPRITE;
        GUIImage sprite = getSprite(gui, path);
        drawSprite(gui, sprite, (int) (vehicle.getPosition().getX() * tileSize) + 2,
                (int) (vehicle.getPosition().getY() * tileSize) + 1);
    }
}
