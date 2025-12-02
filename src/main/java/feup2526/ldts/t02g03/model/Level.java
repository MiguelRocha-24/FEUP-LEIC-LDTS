package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class Level{
    private final Grid grid;
    private final Player player;
    private final List<Lane> lanes;
    private boolean quit = false;
    private boolean gameOver = false;

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
            if (Math.random() < 0.5) {
                RoadLane lane = new RoadLane(dir, speed, i);
                lanes.add(lane);
            } else {
                River lane = new River(i, dir, speed);
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
}
