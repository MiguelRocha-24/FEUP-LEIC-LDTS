package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Lane;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.model.SafeLane;

import java.util.Random;

public class SafeLaneController implements LaneController {
    private Random rng;
    private double spawnChance;

    public SafeLaneController(double spawnChance) {
        if (spawnChance < 0 || spawnChance > 1)
            throw new IllegalArgumentException("Spawn chance must be between 0 and 1");

        this.rng = new Random();
        this.spawnChance = spawnChance;
    }

    @Override
    public void update(Lane lane, Level level) {
        if (!(lane instanceof SafeLane))
            return;

    }
}