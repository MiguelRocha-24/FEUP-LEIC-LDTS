package feup2526.ldts.t02g03.model.game;

public class Player extends MovableEntity {
    private Position targetPosition;

    public Player(Position start) {
        super(start, Direction.UP);
        this.width = 0.5;
        this.offsetX = 0.25;
        this.targetPosition = start;
    }

    public Player(Position start, Direction direction) {
        super(start, direction);
        this.width = 0.5;
        this.offsetX = 0.25;
        this.targetPosition = start;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
    }

    private boolean isOnLog;

    public boolean isOnLog() {
        return isOnLog;
    }

    public void setOnLog(boolean onLog) {
        isOnLog = onLog;
    }

    @Override
    protected Position nextPosition() {
        return switch (direction) {
            case UP -> targetPosition.up();
            case DOWN -> targetPosition.down();
            case LEFT -> targetPosition.left();
            case RIGHT -> targetPosition.right();
        };
    }
}
