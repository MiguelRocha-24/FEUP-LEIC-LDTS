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
        gui.drawText(5, 5, "Menu", "#FFFFFF");

        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            gui.drawText(
                    5, 7 + i,
                    getModel().getEntry(i),
                    getModel().isSelected(i) ? "#FFD700" : "#FFFFFF");
        }
        gui.refresh();
    }
}
