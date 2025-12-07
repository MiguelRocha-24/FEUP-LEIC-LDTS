package feup2526.ldts.t02g03.model.game;

public class Vehicle extends MovableEntity{
    public Vehicle(Position position, Direction direction) {
        super(position, direction);
        this.width = 1.8;
        this.offsetX = 0.1;
    }

    public Position previewNext(){return nextPosition();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return direction == vehicle.direction && position.equals(vehicle.position);
    }
}