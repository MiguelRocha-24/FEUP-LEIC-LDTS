package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RunScoreTest {
    @Test
    void testScoreLogic() {
        RunScore rs = new RunScore();
        assertEquals(0, rs.getCount());
        rs.increment();
        assertEquals(1, rs.getCount());
    }
}
