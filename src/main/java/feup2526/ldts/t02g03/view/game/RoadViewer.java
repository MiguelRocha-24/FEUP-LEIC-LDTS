package feup2526.ldts.t02g03.view.game;

import feup2526.ldts.t02g03.model.game.RoadLane;
import feup2526.ldts.t02g03.model.game.Vehicle;
import feup2526.ldts.t02g03.model.game.Car;
import feup2526.ldts.t02g03.model.game.Bus;
import feup2526.ldts.t02g03.view.GUI;
import feup2526.ldts.t02g03.view.GUIImage;

public class RoadViewer extends SpriteViewer<RoadLane> implements LaneViewer<RoadLane> {
    private final int width;
    private final CarViewer carViewer;
    private final BusViewer busViewer;

    public RoadViewer(int width) {
        super("docs/images/sprites/road.png");
        this.width = width;
        this.carViewer = new CarViewer();
        this.busViewer = new BusViewer();
    }

    @Override
    public void draw(GUI gui, RoadLane lane, int tileSize, int yPos) {
        GUIImage sprite = getSprite(gui);
        for (int i = 0; i < width; i++) {
            drawSprite(gui, sprite, i * tileSize, yPos);
        }
        drawVehicles(gui, lane, tileSize, yPos);
    }

    private void drawVehicles(GUI gui, RoadLane lane, int tileSize, int yPos) {
        for (Vehicle v : lane.getVehicles()) {
            if (v instanceof Car) {
                carViewer.draw(gui, v, tileSize, yPos);
            } else if (v instanceof Bus) {
                busViewer.draw(gui, (Bus) v, tileSize, yPos);
            }
        }
    }
}
