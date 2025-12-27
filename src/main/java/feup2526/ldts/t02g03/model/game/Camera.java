package feup2526.ldts.t02g03.model.game;

public class Camera {
    private double y;
    private double speed;
    private boolean moving;

    public Camera(double startY) {
        this.y = startY;
        this.speed = 0;
        this.moving = false;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void startMoving() {
        this.moving = true;
    }

    private static final double MIN_SPEED = 0.05;

    public void update(double playerY, int screenHeight) {
        if (!moving)
            return;

        double playerScreenY = playerY - y;
        double startThreshold = screenHeight * 0.65;
        double maxThreshold = screenHeight * 0.35;

        //Camera acceleration
        if (playerScreenY > startThreshold) {
            speed = 0;
        } else {
            speed = MIN_SPEED + (startThreshold - playerScreenY) * 0.05;
            y -= speed;

            if (playerY - y < maxThreshold) {
                y = playerY - maxThreshold;
            }
        }
    }
}
