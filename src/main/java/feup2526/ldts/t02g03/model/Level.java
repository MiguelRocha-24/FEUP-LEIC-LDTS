package feup2526.ldts.t02g03.model;
import java.util.ArrayList;
import java.util.List;

public class Level{
    private final Grid grid;
    private final Player player;
    private final List<Lane> lanes;
    private boolean quit = false;

    public Level(int width, int height){
        this.grid = new Grid(width, height);
        this.player = new Player(new Position(width / 2, height - 1));
        this.lanes = new ArrayList<>();

        initializeLevel();
    }

    private void initializeLevel(){
        for (int i = 1; i < grid.getH() - 1; i++) {
            Direction dir = (i % 2 == 0) ? Direction.RIGHT : Direction.LEFT;
            int speed = 1;
            RoadLane lane = new RoadLane(dir, speed, i);
            lanes.add(lane);
        }
    }

    public void quit(){this.quit = true;}
    public boolean isGameOver(){
        if (quit){return true;}
        
        for (Lane lane : lanes) {
            if (lane instanceof RoadLane) {
                RoadLane roadLane = (RoadLane) lane;
                for (Vehicle v : roadLane.getVehicles()) {
                    if (v.getPosition().equals(player.getPosition())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Grid getGrid(){return grid;}
    public Player getPlayer(){return player;}
    public List<Lane> getLanes(){return lanes;}
}
