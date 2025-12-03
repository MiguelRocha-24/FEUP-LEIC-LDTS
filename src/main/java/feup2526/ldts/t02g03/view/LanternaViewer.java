package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
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
                tg.setBackgroundColor(TextColor.Factory.fromString("#336699"));
                tg.fillRectangle(new TerminalPosition(0, lane.getRow() * 16),
                        new TerminalSize(level.getGrid().getW() * 16, 16),
                        ' ');
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
            drawEntityBlock(tg, (int) (l.getPosition().getX() * TILE_SIZE), (int) (l.getPosition().getY() * TILE_SIZE), '=',
                    "#663300", "#336699");
        }
    }

    private void drawPlayer(TextGraphics tg, Player player) {
        playerViewer.draw(tg, player, TILE_SIZE);
    }

    private void drawEntityBlock(TextGraphics tg, int pixelX, int pixelY, char c, String fgColor, String bgColor) {
        tg.setForegroundColor(TextColor.Factory.fromString(fgColor));
        tg.setBackgroundColor(TextColor.Factory.fromString(bgColor));
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                tg.putString(pixelX + i, pixelY + j, String.valueOf(c));
            }
        }
    }

    public KeyStroke readInput() throws IOException {
        return screen.pollInput();
    }

    public void close() throws IOException {
        screen.close();
    }
}
