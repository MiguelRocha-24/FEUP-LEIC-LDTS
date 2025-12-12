package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

import java.util.Random;

public class RoadLaneController extends BaseLaneController {
    private int minSpeed;
    private int maxSpeed;

    public RoadLaneController(double spawnChance, int minSpeed, int maxSpeed, long seed) {
        super(spawnChance, seed);
        if (spawnChance < 0 || spawnChance > 1)
            throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
        if (minSpeed <= 0 || maxSpeed <= 0 || minSpeed > maxSpeed)
            throw new IllegalArgumentException("Invalid speed range");

        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void update(Lane lane, Level level) {
        if (!(lane instanceof RoadLane))
            return;
        RoadLane roadLane = (RoadLane) lane;

        moveVehicles(roadLane);
        spawnVehicles(roadLane, level.getGrid().getW());
        removeOffScreenVehicles(roadLane, level.getGrid().getW());
    }

    private void moveVehicles(RoadLane lane) {
        for (Vehicle vehicle : lane.getVehicles()) {
            Position pos = vehicle.getPosition();
            double speed = lane.getSpeed();
            if (lane.getDirection() == Direction.LEFT) {
                speed = -speed;
            }
            vehicle.setPosition(new Position(pos.getX() + speed, pos.getY()));
        }
    }

    private void spawnVehicles(RoadLane lane, int gridWidth) {
        if (rng.nextDouble() < spawnChance) {
            // Check if there is space to spawn a new vehicle
            boolean canSpawn = true;
            double spawnX = (lane.getDirection() == Direction.RIGHT) ? 0 : gridWidth - 1;

            for (Vehicle v : lane.getVehicles()) {
                if (Math.abs(v.getPosition().getX() - spawnX) < 2.0) { // Minimum distance between vehicles
                    canSpawn = false;
                    break;
                }
            }

            if (canSpawn) {
                // Randomly choose a vehicle type (Car or Bus)
                Vehicle newVehicle;
                if (rng.nextBoolean()) {
                    newVehicle = new Car(new Position(spawnX, lane.getRow()), lane.getDirection());
                } else {
                    newVehicle = new Bus(new Position(spawnX, lane.getRow()), lane.getDirection());
                }
                lane.addVehicle(newVehicle);
            }
        }
    }

    private void removeOffScreenVehicles(RoadLane lane, int gridWidth) {
        lane.getVehicles().removeIf(v -> {
            double x = v.getPosition().getX();
            return x < -2 || x > gridWidth + 2;
        });
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

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setRandom(Random rng) {
        this.rng = rng;
    }

    @Override
    public void handlePhysics(Lane lane, Level level, Position position, boolean isPlayerBody) {
    }
}
