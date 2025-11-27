package feup2526.ldts.t02g03.view;

import feup2526.ldts.t02g03.model.*;

public class Console {

    public void draw(Level level) {
        Grid grid = level.getGrid();
        Player player = level.getPlayer();

        clearScreen();

        for (int y = 0; y < grid.getH(); y++) {
            Lane lane = level.getLaneAtY(y);

            boolean isGrass = (y == 0 || y == grid.getH() - 1);
            boolean isRoad = (y % 3 == 1 && !isGrass);
            boolean isRiver = (!isGrass && !isRoad);

            for (int x = 0; x < grid.getW(); x++) {
                Position position = new Position(x, y);

                if (player.getPosition().equals(position)) {
                    System.out.print("P ");
                } else {
                    Vehicle vehicleAtPosition = null;
                    if (lane != null) {
                        for (Vehicle v : lane.getVehicles()) {
                            if (v.getPosition().equals(position)) {
                                vehicleAtPosition = v;
                                break;
                            }
                        }
                    }

                    if (vehicleAtPosition != null) {
                        System.out.print(vehicleAtPosition.getSymbol() + " ");
                    } else {
                        if (isGrass) System.out.print(". ");
                        else if (isRoad) System.out.print("_ ");
                        else if (isRiver) System.out.print("~ ");
                        else System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }

        System.out.println("\nScore: " + level.getScore());

        if (level.isGameOver()) {
            if (level.isWin()) System.out.println(" YOU WON ");
            else System.out.println(" GAME OVER ");
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
