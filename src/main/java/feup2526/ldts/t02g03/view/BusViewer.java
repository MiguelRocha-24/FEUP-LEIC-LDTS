package feup2526.ldts.t02g03.view;
import feup2526.ldts.t02g03.model.Bus;

public class BusViewer implements Viewer<Bus> {
    @Override
    public char getSymbol(Bus element) {
        return 'B';
    }
}
