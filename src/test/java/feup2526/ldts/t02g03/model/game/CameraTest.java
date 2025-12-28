package feup2526.ldts.t02g03.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void testInitialization() {
        Camera camera = new Camera(100.0);
        assertEquals(100.0, camera.getY());
        assertEquals(0.0, camera.getSpeed());
        assertFalse(camera.isMoving());
    }

    @Test
    void testStartMoving() {
        Camera camera = new Camera(100.0);
        camera.startMoving();
        assertTrue(camera.isMoving());
    }

    @Test
    void testUpdateNotMoving() {
        Camera camera = new Camera(100.0);
        camera.update(50.0, 100);
        assertEquals(100.0, camera.getY()); // Should not have moved
    }

    @Test
    void testUpdateMoving() {
        Camera camera = new Camera(100.0);
        camera.startMoving();
        
        // Player is far up (screenY is small)
        // camera y = 100
        // player y = 20
        // playerScreenY = 20 - 100 = -80
        // startThreshold = 100 * 0.65 = 65
        // playerScreenY < startThreshold -> should move
        
        double initialY = camera.getY();
        camera.update(20.0, 100);
        
        assertTrue(camera.getY() < initialY);
        assertTrue(camera.getSpeed() > 0);
    }
}
