package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

public class LaneGenerationManager {
    private final Level level;
    private int minGeneratedRow;
    private int maxGeneratedRow;

    public LaneGenerationManager(Level level) {
        this.level = level;
        this.maxGeneratedRow = level.getGrid().getH() - 1;
        this.minGeneratedRow = level.getGrid().getH() - 2;
    }

    public void update() {
        Camera camera = level.getCamera();
        int cameraTopRow = (int) camera.getY();
        int generationBuffer = 6;
        int targetMinRow = cameraTopRow - generationBuffer;

        while (minGeneratedRow > targetMinRow) {
            minGeneratedRow--;
            generateLane(minGeneratedRow);
        }

        int cameraBottomRow = (int) (camera.getY() + level.getGrid().getH());
        int cleanupThreshold = cameraBottomRow + 2;

        while (maxGeneratedRow > cleanupThreshold) {
            level.removeLane(maxGeneratedRow);
            maxGeneratedRow--;
        }
    }

    private void generateLane(int row) {
        if (level.getLane(row) != null)
            return;

        Direction dir = (Math.abs(row) % 2 == 0) ? Direction.RIGHT : Direction.LEFT;
        double speed = 0.05 + (Math.abs(row) * 0.0001);
        double choseLane = Math.random();

        Lane lane;
        if (choseLane < 0.33) {
            lane = new RoadLane(dir, speed, row);
        } else if (choseLane < 0.66) {
            lane = new River(row, dir, speed);
        } else {
            lane = new SafeLane(row, level.getGrid().getW(), true);
        }
        level.addLane(row, lane);
    }
}
