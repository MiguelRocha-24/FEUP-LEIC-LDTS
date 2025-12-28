package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;

public class RiverController extends BaseLaneController {
    private static final int MIN_GAP = 3;
    private static final int REMOVE_BUFFER = 2;

    public RiverController(double spawnChance) {
        super(spawnChance);
        if (spawnChance < 0 || spawnChance > 1)
            throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
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
            int cutoff = -REMOVE_BUFFER;
            while (!river.getLogs().isEmpty() && river.getLogs().getFirst().getPosition().getX() < cutoff) {
                river.getLogs().removeFirst();
            }
        } else {
            // Logs leave screen after passing right barrier (x=grid width+removeBuffer)
            int cutoff = grid.getW() + REMOVE_BUFFER;
            while (!river.getLogs().isEmpty() && river.getLogs().getLast().getPosition().getX() > cutoff) {
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
            entryX = -1;
        } else {
            entryX = grid.getW() + 1;
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
            return (firstX - entryX) >= (MIN_GAP + 1);
        } else {
            double lastX = river.getLogs().getLast().getPosition().getX();
            return (entryX - lastX) >= (MIN_GAP + 1);
        }
    }

    public Log getLogAt(River river, Position pos) {
        // adds 0.5 due to player drawing being in the center of a 16x16 tile.
        double centered = pos.getX() + 0.5;
        for (Log log : river.getLogs()) {
            double lMin = log.getPosition().getX();
            double lMax = lMin + log.getWidth();
            if (centered <= lMax && centered >= lMin) {
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

    @Override
    public Position getSnapPosition(Lane lane, Position target) {
        if (!(lane instanceof River))
            return target;
        Log targetLog = getLogAt((River) lane, target);
        if (targetLog != null) {
            double centeredX = targetLog.getPosition().getX();
            return new Position(centeredX, target.getY());
        }
        return target;
    }

    @Override
    public void handlePhysics(Lane lane, Level level, Position position, boolean isPlayerBody) {
        if (!(lane instanceof River))
            return;
        River river = (River) lane;
        Log log = getLogAt(river, position);

        if (log != null) {
            // Center position on the log
            double centeredX = log.getPosition().getX();

            if (isPlayerBody) {
                // Keep player centered on the log
                Position newPos = new Position(centeredX, level.getPlayer().getPosition().getY());
                level.getPlayer().setPosition(newPos);

                // Check if player went off-screen while on log
                Player player = level.getPlayer();
                if (player.getPosition().getX() < -1 || player.getPosition().getX() >= level.getGrid().getW()) {
                    level.handleCollision();
                }
            } else {
                // Keep target position centered on the log too
                Position target = level.getPlayer().getTargetPosition();
                level.getPlayer().setTargetPosition(new Position(centeredX, target.getY()));
            }
        } else if (isPlayerBody) {
            if (level.getPlayer().getPosition().distance(level.getPlayer().getTargetPosition()) < 0.2) {
                level.handleCollision();
            }
        }
    }
}