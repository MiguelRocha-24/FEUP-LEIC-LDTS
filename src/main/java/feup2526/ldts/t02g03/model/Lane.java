package feup2526.ldts.t02g03.model;

public abstract class Lane {
    protected int row;

    public Lane(int row){
        if (row < 0)
            throw new IllegalArgumentException("Row cannot be negative");
        this.row = row;
    }

    public int getRow(){return row;}
    public void update(){};
}
