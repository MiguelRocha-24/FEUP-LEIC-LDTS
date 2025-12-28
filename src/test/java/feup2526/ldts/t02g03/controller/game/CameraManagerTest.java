package feup2526.ldts.t02g03.controller.game;

import feup2526.ldts.t02g03.model.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CameraManagerTest {
    @Test
    void testUpdateStartsMoving() {
        Level mockLevel = Mockito.mock(Level.class);
        Camera mockCamera = Mockito.mock(Camera.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Grid mockGrid = Mockito.mock(Grid.class);
        
        Mockito.when(mockLevel.getCamera()).thenReturn(mockCamera);
        Mockito.when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockLevel.getGrid()).thenReturn(mockGrid);
        
        Mockito.when(mockGrid.getH()).thenReturn(20);
        Mockito.when(mockPlayer.getPosition()).thenReturn(new Position(5, 15)); // 15 < 20-2
        Mockito.when(mockCamera.isMoving()).thenReturn(false);
        
        CameraManager cm = new CameraManager(mockLevel);
        cm.update();
        
        Mockito.verify(mockCamera).startMoving();
        Mockito.verify(mockCamera).update(15.0, 20);
    }
    
    @Test
    void testCheckCollision() {
        Level mockLevel = Mockito.mock(Level.class);
        Camera mockCamera = Mockito.mock(Camera.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Grid mockGrid = Mockito.mock(Grid.class);
        
        Mockito.when(mockLevel.getCamera()).thenReturn(mockCamera);
        Mockito.when(mockLevel.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockLevel.getGrid()).thenReturn(mockGrid);
        
        Mockito.when(mockGrid.getH()).thenReturn(10);
        Mockito.when(mockCamera.getY()).thenReturn(0.0);
        Mockito.when(mockPlayer.getPosition()).thenReturn(new Position(5, 11)); // 11 > 0+10
        
        CameraManager cm = new CameraManager(mockLevel);
        cm.update();
        
        Mockito.verify(mockLevel).handleCollision();
    }
}
