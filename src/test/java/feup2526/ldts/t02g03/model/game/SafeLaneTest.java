package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SafeLaneTest {
    private SafeLane safeLane;

    @BeforeEach
    void setUp() {
        safeLane = new SafeLane(1, 10, false);
    }

    @Test
    void testInitialization() {
        assertNotNull(safeLane.getTrees());
        assertNotNull(safeLane.getCoins());
    }

    @Test
    void testAddTree() {
        Tree t = new Tree(new Position(1, 1));
        safeLane.addTree(t);
        assertEquals(1, safeLane.getTrees().size());
        assertEquals(t, safeLane.getTrees().get(0));
    }

    @Test
    void testAddTreeOrdering() {
        Tree t1 = new Tree(new Position(1, 1));
        Tree t2 = new Tree(new Position(3, 1));
        Tree t3 = new Tree(new Position(2, 1));

        safeLane.addTree(t1);
        safeLane.addTree(t2);
        safeLane.addTree(t3);

        assertEquals(t1, safeLane.getTrees().get(0));
        assertEquals(t3, safeLane.getTrees().get(1));
        assertEquals(t2, safeLane.getTrees().get(2));
    }

    @Test
    void testInvalidAddTree() {
        assertThrows(IllegalArgumentException.class, () -> safeLane.addTree(null));
        assertThrows(IllegalArgumentException.class, () -> safeLane.addTree(new Tree(new Position(1, 2))));
    }

    @Test
    void testAddCoin() {
        Coin c = new Coin(new Position(1, 1));
        safeLane.addCoin(c);
        assertEquals(1, safeLane.getCoins().size());
    }

    @Test
    void testInvalidAddCoin() {
         assertThrows(IllegalArgumentException.class, () -> safeLane.addCoin(null));
         assertThrows(IllegalArgumentException.class, () -> safeLane.addCoin(new Coin(new Position(1, 2))));
    }
}
