package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Position;

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
    public void moveTo(Position target) {
        player.setOnLog(false);
        player.setTargetPosition(target);
    }

    public double getDistanceToTarget() {
        return Math.sqrt(Math.pow(player.getTargetPosition().getX() - player.getPosition().getX(), 2) +
                Math.pow(player.getTargetPosition().getY() - player.getPosition().getY(), 2));
    }
}
