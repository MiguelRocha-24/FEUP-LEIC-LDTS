package feup2526.ldts.t02g03.view;
import feup2526.ldts.t02g03.model.*;
import java.util.List;
import java.util.Arrays;

public class ConsoleViewer {
    private final BusViewer busViewer;
    private final CarViewer carViewer;
    private final PlayerViewer playerViewer;

    public ConsoleViewer() {
        this.busViewer = new BusViewer();
        this.carViewer = new CarViewer();
        this.playerViewer = new PlayerViewer();
    }

    public void draw(Level level) {
        Grid grid = level.getGrid();
        List<Lane> lanes = level.getLanes();
        Player player = level.getPlayer();

        char[][] buffer = new char[grid.getH()][grid.getW()];
        for (char[] row : buffer) {
            Arrays.fill(row, '.');
        }

        for (Lane lane : lanes) {
            if (lane instanceof RoadLane) {
                RoadLane roadLane = (RoadLane) lane;
                for (Vehicle v : roadLane.getVehicles()) {
                    char symbol = 'V';
                    if (v instanceof Bus)
                        symbol = busViewer.getSymbol((Bus) v);
                    else if (v instanceof Car)
                        symbol = carViewer.getSymbol((Car) v);

                    drawOnBuffer(buffer, v.getPosition(), symbol, grid);
                }
            }
        }

        drawOnBuffer(buffer, player.getPosition(), playerViewer.getSymbol(player), grid);
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int y = 0; y < grid.getH(); y++) {
            for (int x = 0; x < grid.getW(); x++) {
                System.out.print(buffer[y][x]);
            }
            System.out.println();
        }
    }

    private void drawOnBuffer(char[][] buffer, Position p, char symbol, Grid grid) {
        if (grid.isInside(p)) {
            buffer[p.getY()][p.getX()] = symbol;
        }
    }
}
