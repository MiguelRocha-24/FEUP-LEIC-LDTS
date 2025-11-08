package feup2526.ldts.t02g03.model;

public abstract class MovableEntity extends Entity {
    protected Direction direction;

    public MovableEntity(Position position, Direction direction) {
        super(position);
        if (direction == null) {throw new IllegalArgumentException("Direction cannot be null");}
        this.direction = direction;
    }

    public Direction getDirection() {return this.direction;}

    public void setDirection(Direction direction) {
        if (direction == null) throw new IllegalArgumentException("Direction cannot be null");
        this.direction = direction;
    }

    protected Position nextPosition() {
        return switch (direction) {
            case UP -> position.up();
            case DOWN -> position.down();
            case LEFT -> position.left();
            case RIGHT -> position.right();
        };
    }
}
