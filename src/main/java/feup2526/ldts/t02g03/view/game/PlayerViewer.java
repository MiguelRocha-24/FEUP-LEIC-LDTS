package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class PlayerViewer extends SpriteViewer<Player> {
    public PlayerViewer() {
        super("docs/images/sprites/chicken-1.png");
    }

    @Override
    public void draw(GUI gui, Player player, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        drawSprite(gui, sprite, (int) (player.getPosition().getX() * tileSize), yPos - 1);
    }
}
