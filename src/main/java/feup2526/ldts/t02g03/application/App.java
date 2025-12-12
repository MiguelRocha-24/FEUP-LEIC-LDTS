package feup2526.ldts.t02g03.application;

import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

import feup2526.ldts.t02g03.view.LanternaGUIFactory;

public class App {
    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        Game game = new Game(new LanternaGUIFactory());
        game.start();

    }
}
