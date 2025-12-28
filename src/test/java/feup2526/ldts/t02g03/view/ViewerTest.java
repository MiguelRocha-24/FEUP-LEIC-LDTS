package feup2526.ldts.t02g03.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class ViewerTest {
    private static class ConcreteViewer extends Viewer<Object> {
        public ConcreteViewer(Object model) {
            super(model);
        }

        @Override
        protected void drawElements(GUI gui) {
            // Do nothing
        }
    }

    @Test
    void testDrawLifecycle() throws IOException {
        Object model = new Object();
        Viewer<Object> viewer = new ConcreteViewer(model);
        GUI mockGUI = Mockito.mock(GUI.class);

        viewer.draw(mockGUI);

        Mockito.verify(mockGUI).clear();
        // drawElements call logic is implicit since we are in a subclass that overrides it, 
        // but checking the template method calls refresh is key.
        Mockito.verify(mockGUI).refresh();
    }
}
