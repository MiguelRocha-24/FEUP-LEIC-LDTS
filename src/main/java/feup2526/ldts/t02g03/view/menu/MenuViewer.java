package feup2526.ldts.t02g03.view.menu;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.LanternaViewer;
import java.io.IOException;

public class MenuViewer extends Viewer<Menu> {
    public MenuViewer(Menu menu) {
        super(menu);
    }

    @Override
    public void draw(LanternaViewer gui) throws IOException {
        gui.clear();

        int terminalWidth = gui.getTerminalWidth();
        int terminalHeight = gui.getTerminalHeight();

        String title = "CROSSY ROADS";
        int titleX = (terminalWidth - title.length()) / 2;
        int titleY = terminalHeight / 4;

        gui.drawText(titleX, titleY, title, "#FFD700");

        int startY = terminalHeight / 2;
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String entry = getModel().getEntry(i);
            int entryX = (terminalWidth - entry.length()) / 2;
            gui.drawText(
                    entryX, startY + (i * 3),
                    entry,
                    getModel().isSelected(i) ? "#FFD700" : "#FFFFFF");
        }
        gui.refresh();
    }
}
