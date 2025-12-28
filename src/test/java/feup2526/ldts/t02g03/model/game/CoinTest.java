package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinTest {
    @Test
    void testCoinCreation() {
        Coin coin = new Coin(new Position(1, 1));
        assertEquals(1.0, coin.getWidth());
        assertEquals(0.0, coin.getOffsetX());
        assertEquals(new Position(1, 1), coin.getPosition());
    }
}
