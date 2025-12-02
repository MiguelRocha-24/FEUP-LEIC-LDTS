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
import feup2526.ldts.t02g03.model.*;

import java.io.IOException;

public class LanternaViewer {
    private final Screen screen;

    public LanternaViewer(int width, int height) throws IOException {
        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(width, height + 1))
                .createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.screen.setCursorPosition(null);
        this.screen.startScreen();
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
                tg.setBackgroundColor(TextColor.Factory.fromString("#333333"));
                tg.fillRectangle(new TerminalPosition(0, lane.getRow()), new TerminalSize(level.getGrid().getW(), 1),
                        ' ');
                drawVehicles(tg, (RoadLane) lane);
            } else if (lane instanceof River) {
                tg.setBackgroundColor(TextColor.Factory.fromString("#336699"));
                tg.fillRectangle(new TerminalPosition(0, lane.getRow()), new TerminalSize(level.getGrid().getW(), 1),
                        ' ');
                drawLogs(tg, (River) lane);
            }
        }
    }

    private void drawVehicles(TextGraphics tg, RoadLane lane) {
        tg.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        tg.setBackgroundColor(TextColor.Factory.fromString("#333333"));
        for (Vehicle v : lane.getVehicles()) {
            tg.putString((int) v.getPosition().getX(), (int) v.getPosition().getY(), "V");
        }
    }

    private void drawLogs(TextGraphics tg, River river) {
        tg.setForegroundColor(TextColor.Factory.fromString("#663300"));
        tg.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        for (Log l : river.getLogs()) {
            tg.putString((int) l.getPosition().getX(), (int) l.getPosition().getY(), "=");
        }
    }

    private void drawPlayer(TextGraphics tg, Player player) {
        tg.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.enableModifiers(com.googlecode.lanterna.SGR.BOLD);
        tg.putString((int) player.getPosition().getX(), (int) player.getPosition().getY(), "P");
    }

    public KeyStroke readInput() throws IOException {
        return screen.pollInput();
    }

    public void close() throws IOException {
        screen.close();
    }
}
