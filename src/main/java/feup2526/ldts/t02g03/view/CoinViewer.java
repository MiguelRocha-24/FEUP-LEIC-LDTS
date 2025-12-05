package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.Coin;
import com.googlecode.lanterna.graphics.TextGraphics;

public class CoinViewer extends SpriteViewer<Coin> {

    public CoinViewer() {
        super("docs/images/sprites/coin.png");
    }

    @Override
    public void draw(TextGraphics tg, Coin coin, int tileSize) {
        drawSprite(tg, sprite, (int) (coin.getPosition().getX() * tileSize),
                (int) (coin.getPosition().getY() * tileSize));
    }
}
