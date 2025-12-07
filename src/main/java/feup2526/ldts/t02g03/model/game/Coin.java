package feup2526.ldts.t02g03.model.game;

public class Coin extends Entity {
    private double width;
    private double offsetX;

    public Coin(Position position) {
        super(position);
        this.width = 1.0;
        this.offsetX = 0.0;
    }

    public double getWidth() {return width;}
    public double getOffsetX() {return offsetX;}
}
