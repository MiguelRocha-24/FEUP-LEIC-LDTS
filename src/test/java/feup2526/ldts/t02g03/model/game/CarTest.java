package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void testCarCreation() {
        Car car = new Car(new Position(1, 1), Direction.LEFT);
        assertEquals(1.8, car.getWidth());
        assertEquals(new Position(1, 1), car.getPosition());
        assertEquals(Direction.LEFT, car.getDirection());
    }
}
