package feup2526.ldts.t02g03.model;

public class Bus extends Vehicle {
    public Bus(Position position, Direction direction) { super(position, direction); }
    public char getSymbol() { return 'B'; }
}
