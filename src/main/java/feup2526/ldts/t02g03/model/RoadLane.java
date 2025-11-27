package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class RoadLane extends MovableLane {
    private List<Vehicle> vehicles;

    public RoadLane(Direction direction, int speed, int row) {
        super(row, direction, speed);
        this.vehicles = new ArrayList<>();
    }

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
