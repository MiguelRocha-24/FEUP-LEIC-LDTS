package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private final Grid grid;
    private final Player player;
    private final List<Lane> lanes;
    private boolean gameOver = false;
    private boolean win = false;
    private final int startPlayerY;

    public Level(int width, int height) {
        this.grid = new Grid(width, height);
        this.player = new Player(new Position(width / 2, height - 1));
        this.startPlayerY = player.getPosition().getY();
        this.lanes = new ArrayList<>();
        generateLanes();
    }

    private void generateLanes() {
        int h = grid.getH();
        Random rng = new Random();

        for (int y = 0; y < h; y++) {
            Direction dir = (y % 2 == 0) ? Direction.RIGHT : Direction.LEFT;

            Lane lane = new Lane(dir, 1, grid, y);

            if (y == 0 || y == h - 1) {
            } else if (y % 2 == 1) {
                populateRoadLane(lane, rng);
            } else {
                // Lane “river” → pode adicionar obstáculos móveis, ou deixar vazia por enquanto
                // Se quiserem implementar logs ou barcos, podem adicionar subclasses de Vehicle
            }

            lanes.add(lane);
        }
    }

    private void populateRoadLane(Lane lane, Random rng) {
        int w = grid.getW();
        Direction dir = lane.getDirection();
        int spacing = 3 + rng.nextInt(3);

        for (int x = 0; x < w; x += spacing) {
            Vehicle v;
            if (rng.nextBoolean()) {
                v = new Car(new Position(x, lane.getRow()), dir);
            } else {
                v = new Bus(new Position(x, lane.getRow()), dir);
            }
            lane.addVehicle(v);
        }
    }

    public void update() {
        if (gameOver) return;

        for (Lane lane : lanes) lane.tick();

        checkCollisions();

        if (player.getPosition().getY() == 0) {
            win = true;
            gameOver = true;
        }
    }

    private void checkCollisions() {
        Position pPos = player.getPosition();
        Lane currentLane = getLaneAtY(pPos.getY());

        if (currentLane != null) {
            for (Vehicle v : currentLane.getVehicles()) {
                if (v.getPosition().equals(pPos)) {
                    gameOver = true;
                    return;
                }
            }
        }
    }

    public Lane getLaneAtY(int y) {
        for (Lane lane : lanes) {
            if (lane.getRow() == y) return lane;
        }
        return null;
    }

    public Player getPlayer() { return player; }
    public Grid getGrid() { return grid; }
    public List<Lane> getLanes() { return new ArrayList<>(lanes); }

    public boolean isGameOver() { return gameOver; }
    public boolean isWin() { return win; }

    public int getScore() {
        int currentY = player.getPosition().getY();
        return Math.max(0, startPlayerY - currentY);
    }
}
