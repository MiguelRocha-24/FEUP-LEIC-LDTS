package feup2526.ldts.t02g03.model;

public abstract class MovableEntity extends Entity {
    protected Direction direction;
    protected double width;
    protected double offsetX;

    public MovableEntity(Position position, Direction direction) {
        super(position);
        if (direction == null) {throw new IllegalArgumentException("Direction cannot be null");}
        this.direction = direction;
        this.width = 1.0;
        this.offsetX = 0.0;
    }

    public double getWidth() {return width;}
    public double getOffsetX() {return offsetX;}
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

    public void move(double distance){
        if (direction == Direction.LEFT){
            this.position = new Position(this.position.getX() - distance, this.position.getY());
        }
        else if (direction == Direction.RIGHT){
            this.position = new Position(this.position.getX() + distance, this.position.getY());
        }
        else if (direction == Direction.UP){
            this.position = new Position(this.position.getX(), this.position.getY() - distance);
        }
        else if (direction == Direction.DOWN){
            this.position = new Position(this.position.getX(), this.position.getY() + distance);
        }
    }

}
