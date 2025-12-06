package feup2526.ldts.t02g03.controller;

import feup2526.ldts.t02g03.model.Lane;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.model.Position;

public interface LaneController {
    void update(Lane lane, Level level);

    void handleCollision(Lane lane, Level level);

    boolean isBlocked(Lane lane, Position pos);
}
