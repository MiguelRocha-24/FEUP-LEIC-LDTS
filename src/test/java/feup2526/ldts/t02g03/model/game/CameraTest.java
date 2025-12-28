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
    void testSetters() {
        Camera camera = new Camera(10.0);
        camera.setY(20.0);
        assertEquals(20.0, camera.getY());

        camera.setSpeed(5.5);
        assertEquals(5.5, camera.getSpeed());
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
        double initialY = camera.getY();
        camera.update(20.0, 100);
        assertTrue(camera.getY() < initialY);
        assertTrue(camera.getSpeed() > 0);
    }

    @Test
    void testUpdateSpeedReducedToZero() {
        Camera camera = new Camera(100.0);
        camera.startMoving();
        camera.setSpeed(5.0);
        camera.update(170.0, 100);
        assertEquals(0.0, camera.getSpeed());
    }
}
