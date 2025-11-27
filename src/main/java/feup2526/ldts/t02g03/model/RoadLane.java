package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class RoadLane extends Lane {
    private Direction direction;
    private int speed;
    private List<Vehicle> vehicles;

    public RoadLane(Direction direction, int speed, int row) {
        super(row);
        if (direction == null) throw new IllegalArgumentException("Lane requires direction");
        if (direction != Direction.LEFT && direction != Direction.RIGHT) throw new IllegalArgumentException("Lane only supports LEFT/RIGHT directions");
        if (speed <= 0) throw new IllegalArgumentException("Speed must be > 0");

        this.direction = direction;
        this.speed = speed;
        this.vehicles = new ArrayList<>();
    }

    public Direction getDirection() {return direction;}
    public int getSpeed() {return speed;}
    public List<Vehicle> getVehicles() {return vehicles;}

    public void addVehicle(Vehicle v) {
        if (v == null) throw new IllegalArgumentException("vehicle required");
        if (v.getPosition().getY() != row) throw new IllegalArgumentException("vehicle must be on this lane");
        if (v.getDirection() != direction) throw new IllegalArgumentException("vehicle must have same direction as lane");

        // Maintain x-order
        int x = v.getPosition().getX();
        int idx = 0;
        while (idx < vehicles.size() && vehicles.get(idx).getPosition().getX() <= x) {idx++;}
        vehicles.add(idx, v);
    }
    public void removeVehicle(Vehicle v) {vehicles.remove(v);}
}
