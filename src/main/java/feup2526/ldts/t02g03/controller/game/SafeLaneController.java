package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.SafeLane;
import feup2526.ldts.t02g03.model.game.Player;
import feup2526.ldts.t02g03.model.game.Position;

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

    @Override
    public void handleCollision(Lane lane, Level level) {
        SafeLane safeLane = (SafeLane) lane;
        Player player = level.getPlayer();

        if (Math.abs(player.getPosition().getY() - safeLane.getRow()) > 0.8)
            return;

        double pMin = player.getPosition().getX() + player.getOffsetX();
        double pMax = pMin + player.getWidth();

        for (int i = 0; i < safeLane.getCoins().size(); i++) {
            feup2526.ldts.t02g03.model.game.Coin c = safeLane.getCoins().get(i);
            double cMin = c.getPosition().getX() + c.getOffsetX();
            double cMax = cMin + c.getWidth();

            if (pMin < cMax && pMax > cMin) {
                safeLane.getCoins().remove(i);
                i--;
                level.getCoinCounter().increment();
            }
        }
    }

    @Override
    public boolean isBlocked(Lane lane, Position pos) {
        if (!(lane instanceof SafeLane))
            return false;
        SafeLane safeLane = (SafeLane) lane;

        double pMin = pos.getX();
        double pMax = pMin + 1.0;

        return safeLane.getTrees().stream().anyMatch(tree -> {
            double tMin = tree.getPosition().getX();
            double tMax = tMin + 1.0;
            return pMin < tMax && pMax > tMin;
        });
    }
}