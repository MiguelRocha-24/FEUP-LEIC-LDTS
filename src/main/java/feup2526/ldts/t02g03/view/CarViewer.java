package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.Vehicle;
import com.googlecode.lanterna.graphics.TextGraphics;

import feup2526.ldts.t02g03.model.Direction;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CarViewer extends SpriteViewer<Vehicle> {
    private BufferedImage carLeft;
    private BufferedImage carRight;

    public CarViewer() {
        super("docs/images/sprites/car.png"); // Default/Fallback
        try {
            carLeft = ImageIO.read(new File("docs/images/sprites/carLeft.png"));
            carRight = ImageIO.read(new File("docs/images/sprites/carRight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(TextGraphics tg, Vehicle vehicle, int tileSize) {
        BufferedImage spriteToDraw = sprite;
        if (vehicle.getDirection() == Direction.LEFT && carLeft != null) {
            spriteToDraw = carLeft;
        } else if (vehicle.getDirection() == Direction.RIGHT && carRight != null) {
            spriteToDraw = carRight;
        }

        drawSprite(tg, spriteToDraw, (int) (vehicle.getPosition().getX() * tileSize),
                (int) (vehicle.getPosition().getY() * tileSize));

    }
}
