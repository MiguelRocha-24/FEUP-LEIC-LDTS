package feup2526.ldts.t02g03.model;

public class Vehicle extends MovableEntity{
    public Vehicle(Position position, Direction direction) {
        super(position, direction);
    }

    public Position previewNext(){
        return nextPosition();
    }

    public void moveOneUnchecked(){
        this.position = nextPosition();
    }

    public void moveUnchecked(int distance) {
        if (distance < 0) throw new IllegalArgumentException("distance must be >= 0");
        for (int i = 0; i < distance; i++) moveOneUnchecked();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return direction == vehicle.direction && position.equals(vehicle.position);
    }
}