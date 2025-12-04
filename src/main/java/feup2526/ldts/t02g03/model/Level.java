package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class Level{
    private final Grid grid;
    private final Player player;
    private final List<Lane> lanes;
    private boolean quit = false;
    private boolean gameOver = false;
    private boolean collisionDetected = false;
    private long collisionTime = 0;

    public Level(int width, int height){
        this.grid = new Grid(width, height);
        this.player = new Player(new Position(width / 2, height - 1));
        this.lanes = new ArrayList<>();

        initializeLevel();
    }

    private void initializeLevel(){
        for (int i = 1; i < grid.getH() - 1; i++) {
            Direction dir = (i % 2 == 0) ? Direction.RIGHT : Direction.LEFT;
            double speed = 0.05;
            double choseLane = Math.random();
            if (choseLane < 0.33) {
                RoadLane lane = new RoadLane(dir, speed, i);
                lanes.add(lane);
            } else if (choseLane < 0.66) {
                River lane = new River(i, dir, speed);
                lanes.add(lane);
            } else {
                SafeLane lane = new SafeLane(i, grid.getW());
                lanes.add(lane);
            }
        }
    }

    public void quit(){this.quit = true;}
    public void setGameOver(boolean state){this.gameOver = state;}
    public boolean isGameOver(){return quit || gameOver;}
    public Grid getGrid(){return grid;}
    public Player getPlayer(){return player;}
    public List<Lane> getLanes(){return lanes;}
    public Lane getLane(int row) {
        if (row < 1 || row >= grid.getH() - 1) return null;
        return lanes.get(row - 1);
    }
    public boolean isCollisionDetected(){return collisionDetected;}
    public long getCollisionTime(){return collisionTime;}
    public void handleCollision() {
        this.collisionDetected = true;
        this.collisionTime = System.currentTimeMillis();
    }

}
