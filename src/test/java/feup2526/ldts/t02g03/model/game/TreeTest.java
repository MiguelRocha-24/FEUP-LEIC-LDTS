package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    @Test
    void testTreeCreation() {
        Tree tree = new Tree(new Position(2, 2));
        assertEquals(new Position(2, 2), tree.getPosition());
    }
}
