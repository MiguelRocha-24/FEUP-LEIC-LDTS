package feup2526.ldts.t02g03.model;

public class Player extends MovableEntity{
    public Player(Position start) {
        super(start, Direction.UP);
    }

    public Player(Position start, Direction direction) {
        super(start, direction);
    }

    public boolean move(Direction dir, Grid grid) {
        if (dir == null) throw new IllegalArgumentException("Direction cannot be null");
        if (grid == null) throw new IllegalArgumentException("Grid cannot be null");

        this.setDirection(dir);
        if (grid.isInside(this.nextPosition())) {
            this.move(1.0);
            return true;
        }

        return false;
    }
}
