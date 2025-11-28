package feup2526.ldts.t02g03.view;
import feup2526.ldts.t02g03.model.Car;

public class CarViewer implements Viewer<Car> {
    @Override
    public char getSymbol(Car element) {
        return 'C';
    }
}
