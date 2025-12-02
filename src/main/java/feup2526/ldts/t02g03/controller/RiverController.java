package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Direction;
import feup2526.ldts.t02g03.model.Grid;
import feup2526.ldts.t02g03.model.Log;
import feup2526.ldts.t02g03.model.River;
import feup2526.ldts.t02g03.model.Position;
import feup2526.ldts.t02g03.model.Vehicle;

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
}