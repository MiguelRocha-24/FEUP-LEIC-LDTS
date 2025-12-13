package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Coin;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;
import java.util.ArrayList;

public class CoinViewer extends SpriteViewer<Coin> {

    private final java.util.List<String> spritePaths;

    public CoinViewer() {
        super("docs/images/sprites/coin1.png");
        spritePaths = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            spritePaths.add("docs/images/sprites/coin" + i + ".png");
        }
    }

    @Override
    public void draw(GUI gui, Coin coin, int tileSize, int yPos) {
        int frame = (int) ((System.currentTimeMillis() / 200) % spritePaths.size());
        GUIImage sprite = getSprite(gui, spritePaths.get(frame));
        drawSprite(gui, sprite, (int) (coin.getPosition().getX() * tileSize), yPos);
    }
}
