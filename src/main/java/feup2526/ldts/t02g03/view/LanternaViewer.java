package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import feup2526.ldts.t02g03.model.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaViewer {
    private final Screen screen;
    private final PlayerViewer playerViewer;
    private final CarViewer carViewer;
    private final RoadViewer roadViewer;
    private final RiverViewer riverViewer;
    private final LogViewer logViewer;
    private static final int TILE_SIZE = 16;

    public LanternaViewer(int width, int height) throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font newfont = font.deriveFont(Font.PLAIN, 4);

        AWTTerminalFontConfiguration cfg = AWTTerminalFontConfiguration.newInstance(newfont);

        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(width * 16, height * 16))
                .setTerminalEmulatorFontConfiguration(cfg)
                .setForceAWTOverSwing(true)
                .createTerminal();

        this.screen = new TerminalScreen(terminal);
        this.screen.setCursorPosition(null);
        this.screen.startScreen();

        this.playerViewer = new PlayerViewer();
        this.carViewer = new CarViewer();
        this.roadViewer = new RoadViewer(width);
        this.riverViewer = new RiverViewer(width);
        this.logViewer = new LogViewer();
    }

    public void draw(Level level) throws IOException {
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();

        drawLanes(tg, level);
        drawPlayer(tg, level.getPlayer());

        screen.refresh();
    }

    private void drawLanes(TextGraphics tg, Level level) {
        for (Lane lane : level.getLanes()) {
            if (lane instanceof RoadLane) {
                roadViewer.draw(tg, (RoadLane) lane, TILE_SIZE);
                drawVehicles(tg, (RoadLane) lane);
            } else if (lane instanceof River) {
                riverViewer.draw(tg, (River) lane, TILE_SIZE);
                drawLogs(tg, (River) lane);
            }
        }
    }

    private void drawVehicles(TextGraphics tg, RoadLane lane) {
        for (Vehicle v : lane.getVehicles()) {
            carViewer.draw(tg, v, TILE_SIZE);
        }
    }

    private void drawLogs(TextGraphics tg, River river) {
        for (Log l : river.getLogs()) {
            logViewer.draw(tg, l, TILE_SIZE);
        }
    }

    private void drawPlayer(TextGraphics tg, Player player) {
        playerViewer.draw(tg, player, TILE_SIZE);
    }

    public KeyStroke readInput() throws IOException {
        return screen.pollInput();
    }

    public void close() throws IOException {
        screen.close();
    }
}
