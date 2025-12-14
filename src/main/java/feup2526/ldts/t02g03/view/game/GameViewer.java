package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.model.game.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GameViewer extends Viewer<Level> {
    private final PlayerViewer playerViewer;
    private final NumberViewer numberViewer;
    private final Map<Class<?>, LaneViewer> viewerMap;
    private static final int TILE_SIZE = 16;

    public GameViewer(Level model) {
        super(model);
        int width = model.getGrid().getW();
        this.playerViewer = new PlayerViewer();
        this.numberViewer = new NumberViewer();
        this.viewerMap = createViewerMap(width);
    }

    private Map<Class<?>, LaneViewer> createViewerMap(int width) {
        Map<Class<?>, LaneViewer> map = new HashMap<>();
        map.put(RoadLane.class, new RoadViewer(width));
        map.put(River.class, new RiverViewer(width));
        map.put(SafeLane.class, new SafeLaneViewer(width));
        return map;
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

            LaneViewer viewer = viewerMap.get(lane.getClass());
            if (viewer != null) {
                viewer.draw(gui, lane, TILE_SIZE, drawY);
            }
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
