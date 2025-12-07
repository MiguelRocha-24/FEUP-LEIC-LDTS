package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Lane;
import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.Position;

public interface LaneController {
    void update(Lane lane, Level level);

    void handleCollision(Lane lane, Level level);

    boolean isBlocked(Lane lane, Position pos);
}
