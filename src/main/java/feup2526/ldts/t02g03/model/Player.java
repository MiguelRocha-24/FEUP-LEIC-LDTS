package feup2526.ldts.t02g03.model;

public class Player extends MovableEntity{
    public Player(Position start) {
        super(start, Direction.UP);
    }

    public Player(Position start, Direction direction) {
        super(start, direction);
    }

    public boolean tryMove(Direction dir, Grid grid) {
        if (dir == null) throw new IllegalArgumentException("Direction cannot be null");
        if (grid == null) throw new IllegalArgumentException("Grid cannot be null");

        setDirection(dir);
        Position next = nextPosition();

        if (grid.isInside(next)) {
            this.position = next;
            return true;
        }
        return false;
    }
}
