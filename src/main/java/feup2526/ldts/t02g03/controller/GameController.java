package feup2526.ldts.t02g03.controller;
import feup2526.ldts.t02g03.model.Level;
import feup2526.ldts.t02g03.model.Direction;
import java.util.Scanner;

public class GameController {
    private final Level level;
    private final Scanner scanner;

    public GameController(Level level) {
        this.level = level;
        this.scanner = new Scanner(System.in);
    }
    public void update() {
        if (level.isGameOver()) return;
        System.out.print("Move (W/A/S/D) + ENTER: ");
        String line = scanner.nextLine().trim().toUpperCase();
        Direction direction = null;

        if (!line.isEmpty()) {
            char c = line.charAt(0);
            switch (c) {
                case 'W' -> direction = Direction.UP;
                case 'S' -> direction = Direction.DOWN;
                case 'A' -> direction = Direction.LEFT;
                case 'D' -> direction = Direction.RIGHT;
                default -> direction = null;
            }
        }
        if (direction != null) {
            level.getPlayer().tryMove(direction, level.getGrid());
        }
        level.update();
    }
}
