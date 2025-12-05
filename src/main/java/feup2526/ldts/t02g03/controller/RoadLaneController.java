package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Direction;
import feup2526.ldts.t02g03.model.Grid;
import feup2526.ldts.t02g03.model.RoadLane;
import feup2526.ldts.t02g03.model.Position;
import feup2526.ldts.t02g03.model.Vehicle;

import java.util.Random;

public class RoadLaneController {
    private RoadLane lane;
    private Grid grid;
    private Random rng;
    private double spawnChance;
    private int minGap;
    private int removeBuffer;
    private int spawnOffset;

    public RoadLaneController(RoadLane lane, Grid grid, double spawnChance, int minGap, int removeBuffer, int spawnOffset) {
        if (lane == null) throw new IllegalArgumentException("Lane required");
        if (grid == null) throw new IllegalArgumentException("Grid required");
        if (spawnChance < 0 || spawnChance > 1) throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
        if (minGap <= 0) throw new IllegalArgumentException("Min gap must be > 0");
        if (removeBuffer <= 0) throw new IllegalArgumentException("Remove buffer must be > 0");
        if (spawnOffset <= 0) throw new IllegalArgumentException("Spawn offset must be > 0");   

        this.lane = lane;
        this.grid = grid;
        this.rng = new Random();
        this.spawnChance = spawnChance;
        this.minGap = minGap;
        this.removeBuffer = removeBuffer;
        this.spawnOffset = spawnOffset;
    }

    public void step() {
        moveVehicles();
        cleanup();
        maybeSpawn();
    }

    private void moveVehicles() {
        for (Vehicle v : lane.getVehicles()) {
            v.move(lane.getSpeed());
        }
    }

    private void cleanup() {
        if (lane.getVehicles().isEmpty()) return;
        if (lane.getDirection() == Direction.LEFT) {
            //Vehicles leave screen after passing left barrier (x=0-removeBuffer)
            int cutoff = -removeBuffer;
            while (!lane.getVehicles().isEmpty() && lane.getVehicles().getFirst().getPosition().getX() < cutoff) {
                lane.getVehicles().removeFirst();
            }
        } else {
            //Vehicles leave screen after passing right barrier (x=grid width+removeBuffer)
            int cutoff = grid.getW() + removeBuffer;
            while (!lane.getVehicles().isEmpty() && lane.getVehicles().getLast().getPosition().getX() > cutoff) {
                lane.getVehicles().removeLast();
            }
        }
    }

    private void maybeSpawn() {
        //Spawn chance is the probability of a vehicle being spawned in a given step
        //If the random number is greater than the spawn chance, no need to spawn vehicle
        if (rng.nextDouble() >= spawnChance) return;
        int entryX;
        if (lane.getDirection() == Direction.RIGHT){
            entryX = -spawnOffset;
        }
        else{
            entryX = grid.getW() + spawnOffset;
        }
        if (!isSpaceForSpawn(entryX)) return;
        Vehicle v = new Vehicle(new Position(entryX, lane.getRow()), lane.getDirection());
        lane.addVehicle(v);
    }

    private boolean isSpaceForSpawn(int entryX) {
        if (lane.getVehicles().isEmpty()) return true;
        if (lane.getDirection() == Direction.RIGHT) {
            double firstX = lane.getVehicles().getFirst().getPosition().getX();
            return (firstX - entryX) >= (minGap + 1);
        } else {
            double lastX = lane.getVehicles().getLast().getPosition().getX();
            return (entryX - lastX) >= (minGap + 1);
        }
    }

    public RoadLane getLane(){return lane;}
    public Grid getGrid(){return grid;}
    public double getSpawnChance(){return spawnChance;}
    public int getMinGap(){return minGap;}
    public int getRemoveBuffer(){return removeBuffer;}
    public int getSpawnOffset(){return spawnOffset;}

    public void setRandom(Random rng){this.rng = rng;}
}
