package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

import java.util.Random;

public class RoadLaneController implements LaneController {
    private Random rng;
    private double spawnChance;
    private int minGap;
    private int removeBuffer;
    private int spawnOffset;

    public RoadLaneController(double spawnChance, int minGap, int removeBuffer, int spawnOffset) {
        if (spawnChance < 0 || spawnChance > 1)
            throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
        if (minGap <= 0)
            throw new IllegalArgumentException("Min gap must be > 0");
        if (removeBuffer <= 0)
            throw new IllegalArgumentException("Remove buffer must be > 0");
        if (spawnOffset <= 0)
            throw new IllegalArgumentException("Spawn offset must be > 0");

        this.rng = new Random();
        this.spawnChance = spawnChance;
        this.minGap = minGap;
        this.removeBuffer = removeBuffer;
        this.spawnOffset = spawnOffset;
    }

    @Override
    public void update(Lane lane, Level level) {
        if (!(lane instanceof RoadLane))
            return;
        RoadLane roadLane = (RoadLane) lane;
        Grid grid = level.getGrid();

        moveVehicles(roadLane);
        cleanup(roadLane, grid);
        maybeSpawn(roadLane, grid);
    }

    private void moveVehicles(RoadLane lane) {
        for (Vehicle v : lane.getVehicles()) {
            v.move(lane.getSpeed());
        }
    }

    private void cleanup(RoadLane lane, Grid grid) {
        if (lane.getVehicles().isEmpty())
            return;
        if (lane.getDirection() == Direction.LEFT) {
            // Vehicles leave screen after passing left barrier (x=0-removeBuffer)
            int cutoff = -removeBuffer;
            while (!lane.getVehicles().isEmpty() && lane.getVehicles().getFirst().getPosition().getX() < cutoff) {
                lane.getVehicles().removeFirst();
            }
        } else {
            // Vehicles leave screen after passing right barrier (x=grid width+removeBuffer)
            int cutoff = grid.getW() + removeBuffer;
            while (!lane.getVehicles().isEmpty() && lane.getVehicles().getLast().getPosition().getX() > cutoff) {
                lane.getVehicles().removeLast();
            }
        }
    }

    private void maybeSpawn(RoadLane lane, Grid grid) {
        // Spawn chance is the probability of a vehicle being spawned in a given step
        // If the random number is greater than the spawn chance, no need to spawn
        // vehicle
        if (rng.nextDouble() >= spawnChance)
            return;
        int entryX;
        if (lane.getDirection() == Direction.RIGHT) {
            entryX = -spawnOffset;
        } else {
            entryX = grid.getW() + spawnOffset;
        }
        if (!isSpaceForSpawn(lane, entryX))
            return;
        Vehicle v = new Vehicle(new Position(entryX, lane.getRow()), lane.getDirection());
        lane.addVehicle(v);
    }

    private boolean isSpaceForSpawn(RoadLane lane, int entryX) {
        if (lane.getVehicles().isEmpty())
            return true;
        if (lane.getDirection() == Direction.RIGHT) {
            double firstX = lane.getVehicles().getFirst().getPosition().getX();
            return (firstX - entryX) >= (minGap + 1);
        } else {
            double lastX = lane.getVehicles().getLast().getPosition().getX();
            return (entryX - lastX) >= (minGap + 1);
        }
    }

    @Override
    public void handleCollision(Lane lane, Level level) {
        RoadLane roadLane = (RoadLane) lane;
        Player player = level.getPlayer();
        if (Math.abs(player.getPosition().getY() - roadLane.getRow()) > 0.8)
            return;
        double pMin = player.getPosition().getX() + player.getOffsetX();
        double pMax = pMin + player.getWidth();

        for (Vehicle v : roadLane.getVehicles()) {
            double vMin = v.getPosition().getX() + v.getOffsetX();
            double vMax = vMin + v.getWidth();

            if (pMin < vMax && pMax > vMin) {
                level.handleCollision();
                return;
            }
        }
    }

    @Override
    public boolean isBlocked(Lane lane, Position pos) {
        return false;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public int getMinGap() {
        return minGap;
    }

    public int getRemoveBuffer() {
        return removeBuffer;
    }

    public int getSpawnOffset() {
        return spawnOffset;
    }

    public void setRandom(Random rng) {
        this.rng = rng;
    }
}
