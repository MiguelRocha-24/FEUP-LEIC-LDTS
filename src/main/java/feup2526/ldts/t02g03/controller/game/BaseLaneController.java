package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.Position;
import java.util.Random;

public abstract class BaseLaneController implements LaneController {
    protected Random rng;
    protected double spawnChance;

    public BaseLaneController(double spawnChance) {
        this.spawnChance = spawnChance;
        this.rng = new Random();
    }

    public BaseLaneController(double spawnChance, long seed) {
        this.spawnChance = spawnChance;
        this.rng = new Random(seed);
    }

    @Override
    public Position getSnapPosition(Lane lane, Position target) {
        return new Position((double) Math.round(target.getX()), (int) Math.round(target.getY()));
    }

    @Override
    public void handlePhysics(Lane lane, Level level, Position position, boolean isPlayerBody) {
        // Default implementation: do nothing (no physics)
    }
}
