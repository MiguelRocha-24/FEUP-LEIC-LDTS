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
    private final AWTTerminalFontConfiguration fontConfig;
    private final Font font;
    private static final int TILE_SIZE = 16;

    public LanternaStarter() throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(baseFont);
        this.font = baseFont.deriveFont(Font.PLAIN, 8);
        this.fontConfig = AWTTerminalFontConfiguration.newInstance(this.font);
    }

    public LanternaViewer createFullscreenViewer() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        FontMetrics fm = new Canvas().getFontMetrics(font);
        int charSize = fm.charWidth('W');
        int cols = screenSize.width / charSize;
        int rows = screenSize.height / charSize;

        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(cols, rows))
                .setTerminalEmulatorFontConfiguration(fontConfig)
                .setForceAWTOverSwing(true)
                .createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();

        return new LanternaViewer(screen);
    }

    public int getGridWidth(LanternaViewer viewer) {
        return viewer.getTerminalWidth() / TILE_SIZE;
    }

    public int getGridHeight(LanternaViewer viewer) {
        return viewer.getTerminalHeight() / TILE_SIZE;
    }
}
