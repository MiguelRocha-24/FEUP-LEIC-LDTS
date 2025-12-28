package feup2526.ldts.t02g03.model.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOverTest {

    @Test
    void testInitialization() {
        GameOver gameOver = new GameOver(100, 200, 50);
        assertEquals(100, gameOver.getScore());
        assertEquals(200, gameOver.getHighScore());
        assertEquals(50, gameOver.getCoins());
        assertEquals(2, gameOver.getNumberEntries());
    }

    @Test
    void testNavigation() {
        GameOver gameOver = new GameOver(0, 0, 0);
        
        assertTrue(gameOver.isSelected(0));
        assertFalse(gameOver.isSelected(1));
        
        gameOver.nextEntry();
        assertTrue(gameOver.isSelected(1));
        
        gameOver.nextEntry();
        assertTrue(gameOver.isSelected(0));
        
        gameOver.previousEntry();
        assertTrue(gameOver.isSelected(1));
    }
}
