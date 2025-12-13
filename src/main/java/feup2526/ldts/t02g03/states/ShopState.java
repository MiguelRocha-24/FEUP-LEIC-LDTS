package feup2526.ldts.t02g03.states;

import feup2526.ldts.t02g03.controller.Controller;
import feup2526.ldts.t02g03.controller.menu.ShopController;
import feup2526.ldts.t02g03.model.menu.Shop;
import feup2526.ldts.t02g03.view.Viewer;
import feup2526.ldts.t02g03.view.menu.ShopViewer;

public class ShopState extends State<Shop> {
    public ShopState(Shop model) {
        super(model);
    }

    @Override
    protected Viewer<Shop> getViewer() {
        return new ShopViewer(getModel());
    }

    @Override
    protected Controller<Shop> getController() {
        return new ShopController(getModel());
    }
}
