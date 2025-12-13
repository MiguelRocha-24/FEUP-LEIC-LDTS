package feup2526.ldts.t02g03.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private final Grid grid;
    private final Player player;
    private final Map<Integer, Lane> lanes;
    private final Camera camera;
    private boolean quit = false;
    private boolean gameOver = false;
    private boolean collisionDetected = false;
    private long collisionTime = 0;
    private final CoinCounter coinCounter;
    private final RunScore runScore;

    public Level(int width, int height) {
        this.grid = new Grid(width, height);
        this.player = new Player(new Position(width / 2, height - 2));
        this.lanes = new HashMap<>();
        this.coinCounter = new CoinCounter();
        this.runScore = new RunScore();
        this.camera = new Camera(0);

        initializeLevel();
    }

    private void initializeLevel() {
        // Generate initial safe zone
        for (int i = 0; i < 2; i++) {
            int row = grid.getH() - 1 - i;
            SafeLane lane = new SafeLane(row, grid.getW(), i == 0);
            if (i == 0) {
                for (int x = 0; x < grid.getW(); x++) {
                    lane.addTree(new Tree(new Position(x, row)));
                }
            }
            lanes.put(row, lane);
        }
    }

    public void addLane(int row, Lane lane) {
        lanes.put(row, lane);
    }

    public void removeLane(int row) {
        lanes.remove(row);
    }

    public void quit() {
        this.quit = true;
    }

    public void setGameOver(boolean state) {
        this.gameOver = state;
    }

    public boolean isGameOver() {
        return quit || gameOver;
    }

    public Grid getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Lane> getLanes() {
        return new ArrayList<>(lanes.values());
    }

    public Lane getLane(int row) {
        return lanes.get(row);
    }

    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public long getCollisionTime() {
        return collisionTime;
    }

    public void handleCollision() {
        this.collisionDetected = true;
        this.collisionTime = System.currentTimeMillis();
    }

    public CoinCounter getCoinCounter() {
        return coinCounter;
    }

    public RunScore getRunScore() {
        return runScore;
    }

    public Camera getCamera() {
        return camera;
    }
}
