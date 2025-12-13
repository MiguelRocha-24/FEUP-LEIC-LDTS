package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class PlayerViewer extends SpriteViewer<Player> {
    private String skinName = "chicken";

    public PlayerViewer() {
        super("docs/images/sprites/chicken.png");
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    @Override
    public void draw(GUI gui, Player player, int tileSize) {
        String path = "docs/images/sprites/" + skinName + ".png";
        GUIImage sprite = getSprite(gui, path);
        drawSprite(gui, sprite, (int) (player.getPosition().getX() * tileSize),
                (int) (player.getPosition().getY() * tileSize));
    }
}
