package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

public class CameraManager {
    private final Level level;

    public CameraManager(Level level) {
        this.level = level;
    }

    public void update() {
        Camera camera = level.getCamera();
        Player player = level.getPlayer();

        if (!camera.isMoving() && player.getPosition().getY() < level.getGrid().getH() - 2) {
            camera.startMoving();
        }

        camera.update(player.getPosition().getY(), level.getGrid().getH());

        if (player.getPosition().getY() > camera.getY() + level.getGrid().getH()) {
            level.handleCollision();
        }
    }
}
