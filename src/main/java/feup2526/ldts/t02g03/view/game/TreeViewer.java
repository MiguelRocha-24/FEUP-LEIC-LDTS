package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Tree;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class TreeViewer extends SpriteViewer<Tree> {

    public TreeViewer() {
        super("docs/images/sprites/tree.png");
    }

    @Override
    public void draw(GUI gui, Tree tree, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        drawSprite(gui, sprite, (int) (tree.getPosition().getX() * tileSize), yPos);
    }
}
