package feup2526.ldts.t02g03.model;

public abstract class Entity {
    protected Position position;
    protected Entity(Position p) {this.position = p;}
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
}