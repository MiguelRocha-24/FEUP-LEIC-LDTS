package feup2526.ldts.t02g03.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovableEntityTest {

    //Stub for testing
    private static class TestMovableEntity extends MovableEntity {
        public TestMovableEntity(Position position, Direction direction) {
            super(position, direction);
        }
        public Position exposeNextPosition() {
            return nextPosition();
        }
    }

    @Test
    void builderTester() {
        TestMovableEntity e = new TestMovableEntity(new Position(1, 2), Direction.UP);
        assertEquals(Direction.UP, e.getDirection());
    }

    @Test
    void builderTester2() {
        try {
            new TestMovableEntity(new Position(0,0), null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("Direction"));
        }
    }


    @Test
    void setterTester() {
        TestMovableEntity e = new TestMovableEntity(new Position(1, 2), Direction.UP);
        e.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, e.getDirection());
    }

    @Test
    void setterTester2() {
        try {
            TestMovableEntity e = new TestMovableEntity(new Position(0,0), Direction.UP);
            e.setDirection(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("Direction"));
        }
    }

    @Test
    void getterTester() {
        TestMovableEntity e = new TestMovableEntity(new Position(1, 2), Direction.UP);
        assertEquals(Direction.UP, e.getDirection());
    }

    @Test
    void nextPositionTester() {
        Position start = new Position(5, 5);

        TestMovableEntity e = new TestMovableEntity(start, Direction.UP);
        assertEquals(new Position(5, 4), e.exposeNextPosition());

        e.setDirection(Direction.DOWN);
        assertEquals(new Position(5, 6), e.exposeNextPosition());

        e.setDirection(Direction.LEFT);
        assertEquals(new Position(4, 5), e.exposeNextPosition());

        e.setDirection(Direction.RIGHT);
        assertEquals(new Position(6, 5), e.exposeNextPosition());
    }



}
