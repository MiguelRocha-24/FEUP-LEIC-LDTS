package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Direction;
import feup2526.ldts.t02g03.model.Grid;
import feup2526.ldts.t02g03.model.Player;
import feup2526.ldts.t02g03.model.Position;

public class PlayerController {
    private final Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        double speed = 0.5;
        Position targetPosition = player.getTargetPosition();
        Position position = player.getPosition();

        double dx = targetPosition.getX() - position.getX();
        double dy = targetPosition.getY() - position.getY();
        moveToTarget(dx, dy, speed);
    }

    private void moveToTarget(double dx, double dy, double speed) {
        double newX = player.getPosition().getX();
        double newY = player.getPosition().getY();

        if (Math.abs(dx) > speed) {
            newX += Math.signum(dx) * speed;
        } else {
            newX = player.getTargetPosition().getX();
        }

        if (Math.abs(dy) > speed) {
            newY += Math.signum(dy) * speed;
        } else {
            newY = player.getTargetPosition().getY();
        }

        player.setPosition(new Position(newX, newY));
    }

    // In PlayerController.java

    // Move ONLY the body (for when standing on a log)
    public void moveBody(double dx) {
        Position pos = player.getPosition();
        player.setPosition(new Position(pos.getX() + dx, pos.getY()));
    }

    // Move ONLY the target (for when the target is on a log)
    public void moveTarget(double dx) {
        Position pos = player.getTargetPosition();
        player.setTargetPosition(new Position(pos.getX() + dx, pos.getY()));
    }

    public void moveTo(Position target) {
        player.setOnLog(false);
        player.setTargetPosition(target);
    }

    public boolean changeTargetPosition(Direction dir, Grid grid) {
        if (dir == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        player.setOnLog(false);
        player.setDirection(dir);
        Position nextPos;
        if (dir == Direction.LEFT) {
            nextPos = new Position(player.getTargetPosition().getX() - 1.0, player.getTargetPosition().getY());
        } else if (dir == Direction.RIGHT) {
            nextPos = new Position(player.getTargetPosition().getX() + 1.0, player.getTargetPosition().getY());
        } else {
            nextPos = switch (dir) {
                case UP -> player.getTargetPosition().up();
                case DOWN -> player.getTargetPosition().down();
                default -> player.getTargetPosition();
            };
        }

        if (grid.isInside(nextPos)) {
            player.setTargetPosition(nextPos);
            return true;
        }

        return false;
    }

    public double getDistanceToTarget() {
        return Math.sqrt(Math.pow(player.getTargetPosition().getX() - player.getPosition().getX(), 2) +
                Math.pow(player.getTargetPosition().getY() - player.getPosition().getY(), 2));
    }
}
