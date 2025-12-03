package feup2526.ldts.t02g03.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElementViewerTest {
    @Test
    void testViewersInstantiation() {
        PlayerViewer playerViewer = new PlayerViewer();
        assertNotNull(playerViewer);

        CarViewer carViewer = new CarViewer();
        assertNotNull(carViewer);

        RoadViewer roadViewer = new RoadViewer(20);
        assertNotNull(roadViewer);
    }
}
