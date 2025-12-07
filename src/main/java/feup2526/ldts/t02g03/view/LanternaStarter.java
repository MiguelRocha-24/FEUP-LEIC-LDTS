package feup2526.ldts.t02g03.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaStarter {
    private final AWTTerminalFontConfiguration menuFontConfig;
    private final AWTTerminalFontConfiguration gameFontConfig;
    private final Font menuFont;
    private final Font gameFont;
    private static final int TILE_SIZE = 16;
    private static final int MENU_FONT_SIZE = 16;
    private static final int GAME_FONT_SIZE = 8;

    public LanternaStarter() throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(baseFont);

        this.menuFont = baseFont.deriveFont(Font.PLAIN, MENU_FONT_SIZE);
        this.gameFont = baseFont.deriveFont(Font.PLAIN, GAME_FONT_SIZE);
        this.menuFontConfig = AWTTerminalFontConfiguration.newInstance(this.menuFont);
        this.gameFontConfig = AWTTerminalFontConfiguration.newInstance(this.gameFont);
    }

    public LanternaViewer createMenuViewer() throws IOException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds();

        FontMetrics fm = new Canvas().getFontMetrics(menuFont);
        int charWidth = fm.charWidth('W');
        int charHeight = fm.getHeight();
        int cols = maxBounds.width / charWidth;
        int rows = maxBounds.height / charHeight;

        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(cols, rows))
                .setTerminalEmulatorFontConfiguration(menuFontConfig)
                .setForceAWTOverSwing(true)
                .setMouseCaptureMode(null)
                .createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();

        return new LanternaViewer(screen);
    }

    public LanternaViewer createGameViewer() throws IOException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds();

        FontMetrics fm = new Canvas().getFontMetrics(gameFont);
        int charSize = fm.charWidth('W');
        int cols = maxBounds.width / charSize;
        int rows = maxBounds.height / charSize;

        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(cols, rows))
                .setTerminalEmulatorFontConfiguration(gameFontConfig)
                .setForceAWTOverSwing(true)
                .createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();

        return new LanternaViewer(screen);
    }

    public int getGridWidth(LanternaViewer viewer) {
        int termWidth = viewer.getTerminalWidth();
        int gridWidth = termWidth / TILE_SIZE;
        return gridWidth;
    }

    public int getGridHeight(LanternaViewer viewer) {
        int termHeight = viewer.getTerminalHeight();
        int gridHeight = termHeight / TILE_SIZE;
        return gridHeight;
    }
}
