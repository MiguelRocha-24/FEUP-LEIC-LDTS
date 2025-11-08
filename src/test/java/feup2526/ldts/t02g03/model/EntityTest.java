package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    private static class DummyEntity extends Entity {
        public DummyEntity(Position position) {
            super(position);
        }
    }

    @Test
    void builderAndGetterTester() {
        Position p = new Position(3, 7);
        Entity e = new DummyEntity(p);
        assertEquals(p, e.getPosition());
    }
}
