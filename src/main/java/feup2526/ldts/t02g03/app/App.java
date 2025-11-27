package feup2526.ldts.t02g03.app;
import feup2526.ldts.t02g03.model.*;
import feup2526.ldts.t02g03.view.Console;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Level level = new Level(10, 10);
        Console console = new Console();
        Scanner scanner = new Scanner(System.in);

        while (!level.isGameOver()) {
            console.draw(level);

            System.out.print("Move (W/A/S/D): ");
            String input = scanner.nextLine().toUpperCase();
            Direction direction = null;

            switch (input) {
                case "W": direction = Direction.UP; break;
                case "S": direction = Direction.DOWN; break;
                case "A": direction = Direction.LEFT; break;
                case "D": direction = Direction.RIGHT; break;
            }

            if (direction != null) {
                level.getPlayer().tryMove(direction, level.getGrid());
            }
            level.update();
        }
        console.draw(level);
        System.out.println("Fim do jogo!");
    }
}
