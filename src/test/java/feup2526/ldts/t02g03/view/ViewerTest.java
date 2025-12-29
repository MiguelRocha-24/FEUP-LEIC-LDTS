package feup2526.ldts.t02g03.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.io.IOException;

class ViewerTest {
    private static class ConcreteViewer extends Viewer<Object> {
        public ConcreteViewer(Object model) {
            super(model);
        }

        @Override
        protected void drawElements(GUI gui) {
        }
    }

    @Test
    void testDrawLifecycle() throws IOException {
        Object model = new Object();
        Viewer<Object> viewer = new ConcreteViewer(model);
        GUI mockGUI = Mockito.mock(GUI.class);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).clear();
        Mockito.verify(mockGUI).refresh();
    }

    @Test
    void testGetModel() {
        Object model = new Object();
        Viewer<Object> viewer = new ConcreteViewer(model);
        assertSame(model, viewer.getModel());
    }
}
