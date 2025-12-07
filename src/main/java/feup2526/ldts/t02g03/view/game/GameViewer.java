package feup2526.ldts.t02g03.view.game;

import com.googlecode.lanterna.graphics.TextGraphics;
import feup2526.ldts.t02g03.model.game.*;
import feup2526.ldts.t02g03.view.LanternaViewer;
import feup2526.ldts.t02g03.view.Viewer;

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

    @Override
    public void draw(LanternaViewer gui) throws IOException {
        gui.clear();
        TextGraphics tg = gui.createTextGraphics();

        drawLanes(tg, getModel());
        drawPlayer(tg, getModel().getPlayer());

        int score = getModel().getRunScore().getCount();
        int scoreWidth = numberViewer.getWidth(score);
        int x = gui.getTerminalWidth() - scoreWidth - 1;
        numberViewer.draw(tg, score, x, 1);

        gui.refresh();
    }

    private void drawLanes(TextGraphics tg, Level level) {
        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                roadViewer.draw(tg, (RoadLane) lane, TILE_SIZE);
                drawVehicles(tg, (RoadLane) lane);
            } else if (lane instanceof River) {
                riverViewer.draw(tg, (River) lane, TILE_SIZE);
                drawLogs(tg, (River) lane);
            } else if (lane instanceof SafeLane) {
                safeLaneViewer.draw(tg, (SafeLane) lane, TILE_SIZE);
                drawTrees(tg, (SafeLane) lane);
                drawCoins(tg, (SafeLane) lane);
            }
        }
    }

    private void drawVehicles(TextGraphics tg, RoadLane lane) {
        for (Vehicle v : lane.getVehicles()) {
            carViewer.draw(tg, v, TILE_SIZE);
        }
    }

    private void drawLogs(TextGraphics tg, River river) {
        for (Log l : river.getLogs()) {
            logViewer.draw(tg, l, TILE_SIZE);
        }
    }

    private void drawTrees(TextGraphics tg, SafeLane lane) {
        for (Tree t : lane.getTrees()) {
            treeViewer.draw(tg, t, TILE_SIZE);
        }
    }

    private void drawCoins(TextGraphics tg, SafeLane lane) {
        for (Coin c : lane.getCoins()) {
            coinViewer.draw(tg, c, TILE_SIZE);
        }
    }

    private void drawPlayer(TextGraphics tg, Player player) {
        playerViewer.draw(tg, player, TILE_SIZE);
    }
}
