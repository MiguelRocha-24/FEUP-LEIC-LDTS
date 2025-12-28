package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinCounterTest {
    @Test
    void testCounterLogic() {
        CoinCounter cc = new CoinCounter();
        assertEquals(0, cc.getCount());
        
        cc.increment();
        assertEquals(1, cc.getCount());
        
        cc.increment(5);
        assertEquals(6, cc.getCount());
        
        cc.decrement();
        assertEquals(5, cc.getCount());
        
        cc.decrement(2);
        assertEquals(3, cc.getCount());
        
        cc.setCount(10);
        assertEquals(10, cc.getCount());
    }
}
