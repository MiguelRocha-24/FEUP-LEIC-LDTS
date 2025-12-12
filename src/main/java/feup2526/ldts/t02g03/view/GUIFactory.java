package feup2526.ldts.t02g03.view;

import java.io.IOException;
import java.awt.FontFormatException;
import java.net.URISyntaxException;

public interface GUIFactory {
    GUI createMenuGUI() throws IOException, FontFormatException, URISyntaxException;

    GUI createGameGUI() throws IOException, FontFormatException, URISyntaxException;

    int getGridWidth(GUI gui);

    int getGridHeight(GUI gui);
}
