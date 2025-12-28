package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.Level;
import feup2526.ldts.t02g03.model.game.Position;
import feup2526.ldts.t02g03.model.menu.User;
import feup2526.ldts.t02g03.model.menu.UserManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {
    @Test
    void testScoreUpdate() {
        Level level = new Level(10, 20); // Player starts at (5, 18)
        UserManager mockUM = Mockito.mock(UserManager.class);
        ScoreManager sm = new ScoreManager(level, mockUM);
        
        assertEquals(0, level.getRunScore().getCount());
        
        // Move player up to 17
        level.getPlayer().setPosition(new Position(5, 17));
        sm.updateScore();
        // 18 - 17 = 1 point
        assertEquals(1, level.getRunScore().getCount());
    }

    @Test
    void testUserStatsUpdate() {
        Level level = new Level(10, 10);
        level.getCoinCounter().setCount(5);
        level.getRunScore().setCount(100);
        
        UserManager mockUM = Mockito.mock(UserManager.class);
        ScoreManager sm = new ScoreManager(level, mockUM);
        
        feup2526.ldts.t02g03.application.Game mockGame = Mockito.mock(feup2526.ldts.t02g03.application.Game.class);
        User user = new User("Test", 0, 0, "A", new ArrayList<>());
        Mockito.when(mockGame.getCurrentUser()).thenReturn(user);
        
        sm.updateUserStats(mockGame);
        
        assertEquals(5, user.getCoins());
        assertEquals(100, user.getHighScore());
        Mockito.verify(mockUM).updateUser(user);
    }
}
