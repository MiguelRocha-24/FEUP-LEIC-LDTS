package feup2526.ldts.t02g03.application;

import com.googlecode.lanterna.input.KeyStroke;
import feup2526.ldts.t02g03.controller.GameController;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.view.LanternaViewer;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        Level level = new Level(10, 10);
        GameController controller = new GameController(level);
        LanternaViewer viewer = new LanternaViewer(10, 10);

        try {
            run(level, controller, viewer);
        } finally {
            viewer.close();
        }
    }

    public static void run(Level level, GameController controller, LanternaViewer viewer) throws IOException {
        // pregenerate lanes
        for (int i = 0; i < 500; i++) {
            controller.updateLanes();
        }
        int FPS = 30;
        int frameTime = 1000 / FPS;
        while (!level.isGameOver()) {
            long startTime = System.currentTimeMillis();
            viewer.draw(level);
            KeyStroke key = viewer.readInput();
            if (key != null) {
                controller.handleInput(key);
            }
            controller.update();
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
