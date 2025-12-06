package feup2526.ldts.t02g03.application;

import feup2526.ldts.t02g03.Game;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        Game game = new Game();
        game.start();

    }
}
