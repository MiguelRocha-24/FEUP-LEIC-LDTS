package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterTest {
    // Concrete implementation for testing
    private static class ConcreteCounter extends Counter {
        public ConcreteCounter() { super(); }
        public ConcreteCounter(int count) { super(count); }
    }
    
    @Test
    void testConstructor() {
        Counter c = new ConcreteCounter(50);
        assertEquals(50, c.getCount());
    }
    
    @Test
    void testLogic() {
        Counter c = new ConcreteCounter();
        assertEquals(0, c.getCount());

        c.increment();
        assertEquals(1, c.getCount());

        c.increment(5);
        assertEquals(6, c.getCount());

        c.decrement();
        assertEquals(5, c.getCount());

        c.decrement(2);
        assertEquals(3, c.getCount());

        c.setCount(10);
        assertEquals(10, c.getCount());
    }
}
