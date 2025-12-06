package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.*;

import java.util.Random;

public class RiverController implements LaneController {
    private Random rng;
    private double spawnChance;
    private int minGap;
    private int removeBuffer;
    private int spawnOffset;

    public RiverController(double spawnChance, int minGap, int removeBuffer, int spawnOffset) {
        if (spawnChance < 0 || spawnChance > 1)
            throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
        if (minGap <= 0)
            throw new IllegalArgumentException("Min gap must be > 0");
        if (removeBuffer <= 0)
            throw new IllegalArgumentException("Remove buffer must be > 0");
        if (spawnOffset <= 0)
            throw new IllegalArgumentException("Spawn offset must be > 0");

        this.rng = new Random();
        this.spawnChance = spawnChance;
        this.minGap = minGap;
        this.removeBuffer = removeBuffer;
        this.spawnOffset = spawnOffset;
    }

    @Override
    public void update(Lane lane, Level level) {
        if (!(lane instanceof River))
            return;
        River river = (River) lane;
        Grid grid = level.getGrid();

        moveLogs(river);
        cleanup(river, grid);
        maybeSpawn(river, grid);
    }

    private void moveLogs(River river) {
        for (Log l : river.getLogs()) {
            l.move(river.getSpeed());
        }
    }

    private void cleanup(River river, Grid grid) {
        if (river.getLogs().isEmpty())
            return;

        if (river.getDirection() == Direction.LEFT) {
            // Logs leave screen after passing left barrier (x=0-removeBuffer)
            int cutoff = -removeBuffer;
            while (river.getLogs().getFirst().getPosition().getX() < cutoff) {
                river.getLogs().removeFirst();
            }
        } else {
            // Logs leave screen after passing right barrier (x=grid width+removeBuffer)
            int cutoff = grid.getW() + removeBuffer;
            while (river.getLogs().getLast().getPosition().getX() > cutoff) {
                river.getLogs().removeLast();
            }
        }
    }

    private void maybeSpawn(River river, Grid grid) {
        // Spawn chance is the probability of a log being spawned in a given step
        // If the random number is greater than the spawn chance, no need to spawn log
        if (rng.nextDouble() >= spawnChance)
            return;
        int entryX;
        if (river.getDirection() == Direction.RIGHT) {
            entryX = -spawnOffset;
        } else {
            entryX = grid.getW() + spawnOffset;
        }

        if (!isSpaceForSpawn(river, entryX))
            return;
        Log l = new Log(new Position(entryX, river.getRow()), river.getDirection());
        river.addLog(l);
    }

    private boolean isSpaceForSpawn(River river, int entryX) {
        if (river.getLogs().isEmpty())
            return true;
        if (river.getDirection() == Direction.RIGHT) {
            double firstX = river.getLogs().getFirst().getPosition().getX();
            return (firstX - entryX) >= (minGap + 1);
        } else {
            double lastX = river.getLogs().getLast().getPosition().getX();
            return (entryX - lastX) >= (minGap + 1);
        }
    }

    public Log getLogAt(River river, Position pos) {
        double centered = pos.getX()+0.5;
        for (Log log : river.getLogs()) {
            double lMin = log.getPosition().getX();
            double lMax = lMin + log.getWidth();
            if (centered < lMax && centered > lMin) {
                return log;
            }
        }
        return null;
    }

    @Override
    public void handleCollision(Lane lane, Level level) {
    }

    @Override
    public boolean isBlocked(Lane lane, Position pos) {
        return false;
    }
}