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
    private final BusViewer busViewer;
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
        this.busViewer = new BusViewer();
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
        double cameraY = level.getCamera().getY();
        int visibleRows = gui.getTerminalHeight() / TILE_SIZE;

        int startRow = (int) Math.floor(cameraY);
        int endRow = startRow + visibleRows + 3; // +3 to cover partial tiles at top/bottom and prevent flickering

        for (int row = startRow; row < endRow; row++) {
            Lane lane = level.getLane(row);
            if (lane == null)
                continue;

            int drawY = (int) ((row - cameraY) * TILE_SIZE);

            if (lane instanceof RoadLane) {
                roadViewer.draw(gui, (RoadLane) lane, TILE_SIZE, drawY);
                drawVehicles(gui, (RoadLane) lane, drawY);
            } else if (lane instanceof River) {
                riverViewer.draw(gui, (River) lane, TILE_SIZE, drawY);
                drawLogs(gui, (River) lane, drawY);
            } else if (lane instanceof SafeLane) {
                safeLaneViewer.draw(gui, (SafeLane) lane, TILE_SIZE, drawY);
                drawTrees(gui, (SafeLane) lane, drawY);
                drawCoins(gui, (SafeLane) lane, drawY);
            }
        }
    }

    private void drawVehicles(GUI gui, RoadLane lane, int yPos) {
        for (Vehicle v : lane.getVehicles()) {
            if (v instanceof Car) {
                carViewer.draw(gui, (Car) v, TILE_SIZE, yPos);
            } else if (v instanceof Bus) {
                busViewer.draw(gui, (Bus) v, TILE_SIZE, yPos);
            }
        }
    }

    private void drawLogs(GUI gui, River river, int yPos) {
        for (Log l : river.getLogs()) {
            logViewer.draw(gui, l, TILE_SIZE, yPos);
        }
    }

    private void drawTrees(GUI gui, SafeLane lane, int yPos) {
        for (Tree t : lane.getTrees()) {
            treeViewer.draw(gui, t, TILE_SIZE, yPos);
        }
    }

    private void drawCoins(GUI gui, SafeLane lane, int yPos) {
        for (Coin c : lane.getCoins()) {
            coinViewer.draw(gui, c, TILE_SIZE, yPos);
        }
    }

    private void drawPlayer(GUI gui, Player player) {
        double cameraY = getModel().getCamera().getY();
        int drawY = (int) ((player.getPosition().getY() - cameraY) * TILE_SIZE);
        boolean isCollision = getModel().isCollisionDetected();
        long collisionTime = getModel().getCollisionTime();
        playerViewer.draw(gui, player, TILE_SIZE, drawY, isCollision, collisionTime);
    }
}
