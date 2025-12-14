package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.SafeLane;
import feup2526.ldts.t02g03.model.game.Tree;
import feup2526.ldts.t02g03.model.game.Coin;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class SafeLaneViewer extends SpriteViewer<SafeLane> implements LaneViewer<SafeLane> {
    private final int width;
    private final TreeViewer treeViewer;
    private final CoinViewer coinViewer;

    public SafeLaneViewer(int width) {
        super("docs/images/sprites/grass2.png");
        this.width = width;
        this.treeViewer = new TreeViewer();
        this.coinViewer = new CoinViewer();
    }

    @Override
    public void draw(GUI gui, SafeLane lane, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, yPos);
        }
        drawTrees(gui, lane, tileSize, yPos);
        drawCoins(gui, lane, tileSize, yPos);
    }

    private void drawTrees(GUI gui, SafeLane lane, int tileSize, int yPos) {
        for (Tree t : lane.getTrees()) {
            treeViewer.draw(gui, t, tileSize, yPos);
        }
    }

    private void drawCoins(GUI gui, SafeLane lane, int tileSize, int yPos) {
        for (Coin c : lane.getCoins()) {
            coinViewer.draw(gui, c, tileSize, yPos);
        }
    }
}
