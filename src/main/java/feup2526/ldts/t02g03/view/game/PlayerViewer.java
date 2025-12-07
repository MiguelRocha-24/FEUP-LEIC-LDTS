package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Player;
import com.googlecode.lanterna.graphics.TextGraphics;

public class PlayerViewer extends SpriteViewer<Player> {
    public PlayerViewer() {
        super("docs/images/sprites/chicken-1.png");
    }

    @Override
    public void draw(TextGraphics tg, Player player, int tileSize) {
        drawSprite(tg, sprite, (int) (player.getPosition().getX() * tileSize),
                (int) (player.getPosition().getY() * tileSize));
    }
}
