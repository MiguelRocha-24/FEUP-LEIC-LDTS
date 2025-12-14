package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.MenuController;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.MenuViewer;

public class MenuState extends State<Menu> {
    public MenuState(Menu model) {
        super(model);
    }

    @Override
    protected Viewer<Menu> createViewer() {
        return new MenuViewer(getModel());
    }

    @Override
    protected Controller<Menu> createController() {
        return new MenuController(getModel());
    }
}
