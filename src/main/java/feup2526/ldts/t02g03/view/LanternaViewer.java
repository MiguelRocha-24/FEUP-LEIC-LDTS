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
import com.googlecode.lanterna.*;

import feup2526.ldts.t02g03.model.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.image.BufferedImage;

public class LanternaViewer {
    private final Screen screen;
    private BufferedImage chickenSprite;

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

        try {
            BufferedImage originalImage = javax.imageio.ImageIO.read(new File("docs/images/sprites/chicken.png"));
            chickenSprite = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2d = chickenSprite.createGraphics();
            g2d.drawImage(originalImage, 0, 0, 16, 16, null);
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback or handle error
        }
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
                tg.fillRectangle(new TerminalPosition(0, lane.getRow() * 16),
                        new TerminalSize(level.getGrid().getW() * 16, 16),
                        ' ');
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
            drawEntityBlock(tg, (int) (v.getPosition().getX() * 16), (int) (v.getPosition().getY() * 16), 'V',
                    "#FF0000", "#333333");
        }
    }

    private void drawLogs(TextGraphics tg, River river) {
        for (Log l : river.getLogs()) {
            drawEntityBlock(tg, (int) (l.getPosition().getX() * 16), (int) (l.getPosition().getY() * 16), '=',
                    "#663300", "#336699");
        }
    }

    private void drawPlayer(TextGraphics tg, Player player) {
        if (chickenSprite != null) {
            drawSprite(tg, chickenSprite, (int) (player.getPosition().getX() * 16),
                    (int) (player.getPosition().getY() * 16));
        } else {
            drawEntityBlock(tg, (int) (player.getPosition().getX() * 16), (int) (player.getPosition().getY() * 16), 'P',
                    "#00FF00", "#000000");
        }
    }

    private void drawEntityBlock(TextGraphics tg, int pixelX, int pixelY, char c, String fgColor, String bgColor) {
        tg.setForegroundColor(TextColor.Factory.fromString(fgColor));
        tg.setBackgroundColor(TextColor.Factory.fromString(bgColor));
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                tg.putString(pixelX + i, pixelY + j, String.valueOf(c));
            }
        }
    }

    private void drawSprite(TextGraphics graphics, BufferedImage sprite, int xPos, int yPos) {
        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                int a = sprite.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha > 128) {
                    TextCharacter c = TextCharacter.fromCharacter(' ',
                            new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue))[0];
                    graphics.setCharacter(xPos + x, yPos + y, c);
                }
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
