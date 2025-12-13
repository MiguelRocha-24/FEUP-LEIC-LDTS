package feup2526.ldts.t02g03.model.game;

public abstract class Lane {
    protected int row;

    public Lane(int row) {
        this.row = row;
    }

    public int getRow(){return row;}
    public void update(){};
}
