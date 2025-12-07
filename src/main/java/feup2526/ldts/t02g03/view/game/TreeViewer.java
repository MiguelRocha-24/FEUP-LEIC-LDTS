package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.Tree;
import com.googlecode.lanterna.graphics.TextGraphics;

public class TreeViewer extends SpriteViewer<Tree>{

    public TreeViewer() {
        super("docs/images/sprites/tree.png");
    }

    @Override
    public void  draw(TextGraphics tg, Tree tree, int tileSize) {
        drawSprite(tg, sprite, (int) (tree.getPosition().getX() * tileSize),
                (int) (tree.getPosition().getY() * tileSize));
    }
}
