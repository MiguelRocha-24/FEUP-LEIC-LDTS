package feup2526.ldts.t02g03.model;

public abstract class MovableLane extends Lane {
    protected Direction direction;
    protected int speed;

    public MovableLane(int row, Direction direction, int speed) {
        super(row);
        if (direction == null) throw new IllegalArgumentException("Lane requires direction");
        if (direction != Direction.LEFT && direction != Direction.RIGHT) throw new IllegalArgumentException("Lane only supports LEFT/RIGHT directions");
        if (speed <= 0) throw new IllegalArgumentException("Speed must be > 0");

        this.direction = direction;
        this.speed = speed;
    }

    public Direction getDirection(){return direction;}

    public int getSpeed(){return speed;}
}
