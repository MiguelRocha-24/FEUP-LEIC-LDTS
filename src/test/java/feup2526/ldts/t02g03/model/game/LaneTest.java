package feup2526.ldts.t02g03.model.game;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import feup2526.ldts.t02g03.model.game.Lane;

public class LaneTest {
    private class TestLane extends Lane {
        public TestLane(int row) {
            super(row);
        }
    }

    @Test
    public void builderAndGetterTester() {
        Lane lane = new TestLane(5);
        assertEquals(5, lane.getRow());
    }


}
