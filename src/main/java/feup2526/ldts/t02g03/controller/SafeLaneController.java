package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Grid;
import feup2526.ldts.t02g03.model.SafeLane;

import java.util.Random;

public class SafeLaneController {
    private SafeLane safeLane;
    private Grid grid;
    private Random rng;
    private double spawnChance;

    public SafeLaneController(SafeLane safeLane, Grid grid, double spawnChance) {
        if (safeLane == null) throw new IllegalArgumentException("SafeLane required");
        if (grid == null) throw new IllegalArgumentException("Grid required");
        if (spawnChance < 0 || spawnChance > 1) throw new IllegalArgumentException("Spawn chance must be between 0 and 1");

        this.safeLane = safeLane;
        this.grid = grid;
        this.rng = new Random();
        this.spawnChance = spawnChance;
    }

    public void step() {
    }
}