package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Coin;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.util.ArrayList;

public class CoinViewer extends SpriteViewer<Coin> {

    private java.util.List<com.googlecode.lanterna.TextCharacter[][]> sprites;

    public CoinViewer() {
        super("docs/images/sprites/coin1.png");
        sprites = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            sprites.add(loadSprite("docs/images/sprites/coin" + i + ".png"));
        }
    }

    @Override
    public void draw(TextGraphics tg, Coin coin, int tileSize) {
        int frame = (int) ((System.currentTimeMillis() / 200) % sprites.size());
        drawSprite(tg, sprites.get(frame), (int) (coin.getPosition().getX() * tileSize),
                (int) (coin.getPosition().getY() * tileSize));
    }
}
