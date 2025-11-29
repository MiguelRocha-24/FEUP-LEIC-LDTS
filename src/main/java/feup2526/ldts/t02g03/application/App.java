package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.controller.GameController;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.view.ConsoleViewer;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Level level = new Level(20, 7);
        GameController controller = new GameController(level);
        ConsoleViewer viewer = new ConsoleViewer();

        // pregenerate lanes
        for (int i = 0; i < 30; i++){
            controller.updateLanes();
        }

        viewer.draw(level);
        while(!level.isGameOver()){
            if (controller.updatePlayer()){
                viewer.draw(level);
                }
            }
    }
}
