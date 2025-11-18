package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lane {
    private Direction direction;
    private int speed;
    private Grid grid;
    private int row;
    private List<Vehicle> vehicles = new ArrayList<>();
    private Random rng;
    private double spawnChance;
    private int minGap;
    private int removeBuffer;
    private int spawnOffset;

    public Lane(Direction direction, int speed, Grid grid, int row) {
        this(direction, speed, grid, row, new Random(), 0.0, 2, 2, 2);
    }

    public Lane(Direction direction, int speed, Grid grid, int row, Random rng, double spawnChance, int minGap, int removeBuffer, int spawnOffset) {

        if (direction == null) throw new IllegalArgumentException("Lane requires direction");
        if (direction != Direction.LEFT && direction != Direction.RIGHT) throw new IllegalArgumentException("Lane only supports LEFT/RIGHT directions");
        if (speed <= 0) throw new IllegalArgumentException("Speed must be > 0");
        if (grid == null) throw new IllegalArgumentException("Grid required");
        if (rng == null) throw new IllegalArgumentException("Rng required");
        if (spawnChance < 0.0 || spawnChance > 1.0) throw new IllegalArgumentException("spawnChance must be between 0 and 1");
        if (minGap < 0) throw new IllegalArgumentException("minGap must be at least 0");
        if (removeBuffer < 0) throw new IllegalArgumentException("removeBuffer must be at least 0");
        if (spawnOffset < 0) throw new IllegalArgumentException("spawnOffset must be at least 0");

        this.direction = direction;
        this.speed = speed;
        this.grid = grid;
        this.row = row;
        this.rng = rng;
        this.spawnChance = spawnChance;
        this.minGap = minGap;
        this.removeBuffer = removeBuffer;
        this.spawnOffset = spawnOffset;
    }


    public void tick() {
        if (vehicles.isEmpty()) {
            maybeSpawn();
            return;
        }

        for (Vehicle v : vehicles) {
            v.setDirection(direction);
            v.moveUnchecked(speed);
        }


        if (direction == Direction.LEFT) {
            int cutoff = -removeBuffer;
            while (!vehicles.isEmpty() && vehicles.getFirst().getPosition().getX() < cutoff) {
                vehicles.removeFirst();
            }
        } else {
            int cutoff = grid.getW() + removeBuffer;
            while (!vehicles.isEmpty() && vehicles.getLast().getPosition().getX() > cutoff) {
                vehicles.removeLast();
            }
        }


        maybeSpawn();
    }

    // ---- Spawning (horizontal only) ----

    /**
     * RNG behavior:
     * - Each tick we draw u ~ U(0,1). If u < spawnChance, we ATTEMPT to spawn.
     * - We then enforce spacing: ensure at least 'minGap' empty tiles from the entry to the nearest vehicle.
     * - If clear, we spawn one vehicle at the entry WITH an optional negative offset (spawnOffset) so it slides in smoothly.
     *
     * Why offset? Rendering terminals can flicker if we spawn exactly at the first visible cell and delete exactly at
     * the last visible cell. Offsets (+removeBuffer) move the spawn/cull slightly outside the grid → smoother visuals.
     */
    void maybeSpawn() {
        if (spawnChance <= 0.0) return;
        if (rng.nextDouble() >= spawnChance) return;

        final int entryX = (direction == Direction.RIGHT) ? -spawnOffset : grid.getW() - 1 + spawnOffset;
        final Position entry = new Position(entryX, row);

        if (!isSpaceForSpawn(entryX)) return;

        // Insert at the correct end to preserve sorted-by-x invariant.
        Vehicle v = new Vehicle(entry, direction);
        if (direction == Direction.RIGHT) {
            // entry is on the left side; put at headgetWidth
            vehicles.add(0, v);
        } else {
            // entry is on the right side; put at tail
            vehicles.add(v);
        }
    }

    /**
     * O(1) spacing check using ordering:
     * - RIGHT: vehicles are x-ascending. The closest to entry is the head (index 0).
     *          We require: vehicles.isEmpty() OR vehicles.get(0).x - entryX >= minGap + 1
     * - LEFT:  closest to entry is the tail (last index).
     *          We require: vehicles.isEmpty() OR entryX - vehicles.get(last).x >= minGap + 1
     */
    private boolean isSpaceForSpawn(int entryX) {
        if (vehicles.isEmpty()) return true;

        if (direction == Direction.RIGHT) {
            int firstX = vehicles.get(0).getPosition().getX();
            return (firstX - entryX) >= (minGap + 1);
        } else { // LEFT
            int lastX = vehicles.get(vehicles.size() - 1).getPosition().getX();
            return (entryX - lastX) >= (minGap + 1);
        }
    }

    // ---- API / helpers ----

    public Direction getDirection() { return direction; }
    public int getSpeed() { return speed; }
    public Grid getGrid() { return grid; }
    public int getRow() { return row; }
    public List<Vehicle> getVehicles() { return vehicles; }

    /**
     * Adds a vehicle already inside (or slightly outside for tests) and keeps x-ordering.
     * Precondition: the vehicle y must equal the lane row.
     */
    public void addVehicle(Vehicle v) {
        if (v == null) throw new IllegalArgumentException("vehicle required");
        if (v.getPosition().getY() != row) throw new IllegalArgumentException("vehicle must be on this lane row");

        // Insert keeping x-ascending order; linear scan is typically tiny because per-lane counts are small.
        int x = v.getPosition().getX();
        int idx = 0;
        while (idx < vehicles.size() && vehicles.get(idx).getPosition().getX() <= x) idx++;
        vehicles.add(idx, v);
    }
}

