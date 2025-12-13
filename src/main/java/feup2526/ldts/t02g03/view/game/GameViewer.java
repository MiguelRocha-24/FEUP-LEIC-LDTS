package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.model.game.*;

import java.io.IOException;

public class GameViewer extends Viewer<Level> {
    private final PlayerViewer playerViewer;
    private final CarViewer carViewer;
    private final RoadViewer roadViewer;
    private final RiverViewer riverViewer;
    private final SafeLaneViewer safeLaneViewer;
    private final LogViewer logViewer;
    private final TreeViewer treeViewer;
    private final CoinViewer coinViewer;
    private final NumberViewer numberViewer;
    private static final int TILE_SIZE = 16;

    public GameViewer(Level model) {
        super(model);
        int width = model.getGrid().getW();
        this.playerViewer = new PlayerViewer();
        this.carViewer = new CarViewer();
        this.roadViewer = new RoadViewer(width);
        this.riverViewer = new RiverViewer(width);
        this.safeLaneViewer = new SafeLaneViewer(width);
        this.logViewer = new LogViewer();
        this.treeViewer = new TreeViewer();
        this.coinViewer = new CoinViewer();
        this.numberViewer = new NumberViewer();
    }

    public void setPlayerSkin(String skin) {
        playerViewer.setSkinName(skin);
    }

    @Override
    protected void drawElements(GUI gui) throws IOException {
        drawLanes(gui, getModel());
        drawPlayer(gui, getModel().getPlayer());

        int score = getModel().getRunScore().getCount();
        int scoreWidth = numberViewer.getWidth(gui, score);
        int x = gui.getTerminalWidth() - scoreWidth - 1;
        numberViewer.draw(gui, score, x, 1);
    }

    private void drawLanes(GUI gui, Level level) {
        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                roadViewer.draw(gui, (RoadLane) lane, TILE_SIZE);
                drawVehicles(gui, (RoadLane) lane);
            } else if (lane instanceof River) {
                riverViewer.draw(gui, (River) lane, TILE_SIZE);
                drawLogs(gui, (River) lane);
            } else if (lane instanceof SafeLane) {
                safeLaneViewer.draw(gui, (SafeLane) lane, TILE_SIZE);
                drawTrees(gui, (SafeLane) lane);
                drawCoins(gui, (SafeLane) lane);
            }
        }
    }

    private void drawVehicles(GUI gui, RoadLane lane) {
        for (Vehicle v : lane.getVehicles()) {
            carViewer.draw(gui, v, TILE_SIZE);
        }
    }

    private void drawLogs(GUI gui, River river) {
        for (Log l : river.getLogs()) {
            logViewer.draw(gui, l, TILE_SIZE);
        }
    }

    private void drawTrees(GUI gui, SafeLane lane) {
        for (Tree t : lane.getTrees()) {
            treeViewer.draw(gui, t, TILE_SIZE);
        }
    }

    private void drawCoins(GUI gui, SafeLane lane) {
        for (Coin c : lane.getCoins()) {
            coinViewer.draw(gui, c, TILE_SIZE);
        }
    }

    private void drawPlayer(GUI gui, Player player) {
        playerViewer.draw(gui, player, TILE_SIZE);
    }
}
