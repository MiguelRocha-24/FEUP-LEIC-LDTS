package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.RemoveUserController;
import feup2526.ldts.t02g03.model.menu.RemoveUser;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.RemoveUserViewer;

public class RemoveUserState extends State<RemoveUser> {
    public RemoveUserState(RemoveUser model) {
        super(model);
    }

    @Override
    protected Viewer<RemoveUser> createViewer() {
        return new RemoveUserViewer(getModel());
    }

    @Override
    protected Controller<RemoveUser> createController() {
        return new RemoveUserController(getModel());
    }
}
