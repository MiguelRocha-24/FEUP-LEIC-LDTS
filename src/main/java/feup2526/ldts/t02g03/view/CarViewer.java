package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.Vehicle;
import com.googlecode.lanterna.graphics.TextGraphics;

public class CarViewer extends SpriteViewer<Vehicle> {
    public CarViewer() {
        super("docs/images/sprites/car.png");

    }

    @Override
    public void draw(TextGraphics tg, Vehicle vehicle, int tileSize) {
        drawSprite(tg, sprite, (int) (vehicle.getPosition().getX() * tileSize),
                (int) (vehicle.getPosition().getY() * tileSize));

    }
}
