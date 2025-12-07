package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.NewUserController;
import feup2526.ldts.t02g03.model.menu.Menu;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.NewUserViewer;

public class NewUserState extends State<Menu> {
    public NewUserState(Menu model) {
        super(model);
    }

    @Override
    protected Viewer<Menu> getViewer() {
        return new NewUserViewer(getModel());
    }

    @Override
    protected Controller<Menu> getController() {
        return new NewUserController(getModel());
    }
}
