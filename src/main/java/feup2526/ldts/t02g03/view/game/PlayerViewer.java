package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Direction;
import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class PlayerViewer extends SpriteViewer<Player> {
    private String leftSprite;
    private String rightSprite;
    private Direction lastHorizontalDirection = Direction.RIGHT;

    public PlayerViewer() {
        super("docs/images/sprites/chickenRight.png");
        setSkinName("chicken");
    }

    public void setSkinName(String skinName) {
        this.leftSprite = "docs/images/sprites/" + skinName + "Left.png";
        this.rightSprite = "docs/images/sprites/" + skinName + "Right.png";
    }

    @Override
    public void draw(GUI gui, Player player, int tileSize, int yPos) {
        Direction direction = player.getDirection();
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            lastHorizontalDirection = direction;
        }
        String path = (lastHorizontalDirection == Direction.LEFT) ? leftSprite : rightSprite;
        GUIImage sprite = getSprite(gui, path);
        drawSprite(gui, sprite, (int) (player.getPosition().getX() * tileSize), yPos - 1);
    }
}
