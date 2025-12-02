package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Direction;
import feup2526.ldts.t02g03.model.Grid;
import feup2526.ldts.t02g03.model.Log;
import feup2526.ldts.t02g03.model.River;
import feup2526.ldts.t02g03.model.Position;

import java.util.Random;

public class RiverController {
    private River river;
    private Grid grid;
    private Random rng;
    private double spawnChance;
    private int minGap;
    private int removeBuffer;
    private int spawnOffset;

    public RiverController(River river, Grid grid, double spawnChance, int minGap, int removeBuffer, int spawnOffset) {
        if (river == null) throw new IllegalArgumentException("River required");
        if (grid == null) throw new IllegalArgumentException("Grid required");
        if (spawnChance < 0 || spawnChance > 1) throw new IllegalArgumentException("Spawn chance must be between 0 and 1");
        if (minGap <= 0) throw new IllegalArgumentException("Min gap must be > 0");
        if (removeBuffer <= 0) throw new IllegalArgumentException("Remove buffer must be > 0");
        if (spawnOffset <= 0) throw new IllegalArgumentException("Spawn offset must be > 0");   
        
        this.river = river;
        this.grid = grid;
        this.rng = new Random();
        this.spawnChance = spawnChance;
        this.minGap = minGap;
        this.removeBuffer = removeBuffer;
        this.spawnOffset = spawnOffset;
    }

    public void step() {
        moveLogs();
        cleanup();
        maybeSpawn();
    }

    private void moveLogs() {
        for (Log l : river.getLogs()) {
            l.move(river.getSpeed());
        }
    }

    private void cleanup() {
        if (river.getLogs().isEmpty()) return;

        if (river.getDirection() == Direction.LEFT) {
            //Logs leave screen after passing left barrier (x=0-removeBuffer)
            int cutoff = -removeBuffer;
            while (river.getLogs().getFirst().getPosition().getX() < cutoff) {
                river.getLogs().removeFirst();
            }
        } else {
            //Logs leave screen after passing right barrier (x=grid width+removeBuffer)
            int cutoff = grid.getW() + removeBuffer;
            while (river.getLogs().getLast().getPosition().getX() > cutoff) {
                river.getLogs().removeLast();
            }
        }
    }

    private void maybeSpawn() {
        //Spawn chance is the probability of a log being spawned in a given step
        //If the random number is greater than the spawn chance, no need to spawn log
        if (rng.nextDouble() >= spawnChance) return;
        int entryX;
        if (river.getDirection() == Direction.RIGHT){
            entryX = -spawnOffset;
        }
        else{
            entryX = grid.getW() + spawnOffset;
        }
        

        if (!isSpaceForSpawn(entryX)) return;
        Log l = new Log(new Position(entryX, river.getRow()), river.getDirection());
        river.addLog(l);
    }

    private boolean isSpaceForSpawn(int entryX) {
        if (river.getLogs().isEmpty()) return true;
        if (river.getDirection() == Direction.RIGHT) {
            double firstX = river.getLogs().getFirst().getPosition().getX();
            return (firstX - entryX) >= (minGap + 1);
        } else {
            double lastX = river.getLogs().getLast().getPosition().getX();
            return (entryX - lastX) >= (minGap + 1);
        }
    }

}