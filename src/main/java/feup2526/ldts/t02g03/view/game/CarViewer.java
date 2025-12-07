package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.Vehicle;
import com.googlecode.lanterna.graphics.TextGraphics;

import feup2526.ldts.t02g03.model.Direction;
import com.googlecode.lanterna.TextCharacter;

public class CarViewer extends SpriteViewer<Vehicle> {
    private TextCharacter[][] carLeft;
    private TextCharacter[][] carRight;

    public CarViewer() {
        super("docs/images/sprites/carLeft.png"); // Default/Fallback
        carLeft = loadSprite("docs/images/sprites/carLeft.png");
        carRight = loadSprite("docs/images/sprites/carRight.png");
    }

    @Override
    public void draw(TextGraphics tg, Vehicle vehicle, int tileSize) {
        TextCharacter[][] spriteToDraw = sprite;
        if (vehicle.getDirection() == Direction.LEFT) {
            spriteToDraw = carLeft;
        } else if (vehicle.getDirection() == Direction.RIGHT) {
            spriteToDraw = carRight;
        }

        drawSprite(tg, spriteToDraw, (int) (vehicle.getPosition().getX() * tileSize),
                (int) (vehicle.getPosition().getY() * tileSize));

    }
}
