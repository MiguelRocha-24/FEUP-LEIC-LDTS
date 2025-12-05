package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.Coin;
import com.googlecode.lanterna.graphics.TextGraphics;

public class CoinViewer extends SpriteViewer<Coin> {

    private java.util.List<java.awt.image.BufferedImage> sprites;

    public CoinViewer() {
        super("docs/images/sprites/coin1.png");
        sprites = new java.util.ArrayList<>();
        try {
            for (int i = 1; i <= 4; i++) {
                sprites.add(javax.imageio.ImageIO.read(new java.io.File("docs/images/sprites/coin" + i + ".png")));
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(TextGraphics tg, Coin coin, int tileSize) {
        int frame = (int) ((System.currentTimeMillis() / 200) % sprites.size());
        drawSprite(tg, sprites.get(frame), (int) (coin.getPosition().getX() * tileSize),
                (int) (coin.getPosition().getY() * tileSize));
    }
}
