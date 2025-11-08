package feup2526.ldts.t02g03.model;

public final class Position {
    private int x,y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public Position up(){return new Position(x,y-1);}
    public Position down(){return new Position(x,y+1);}
    public Position left(){return new Position(x-1,y);}
    public Position right(){return new Position(x+1,y);}
    public Position translate(int dx, int dy) {
        return new Position(x+dx,y+dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position p = (Position) o;
        return this.x == p.getX() && this.y == p.getY();
    }

}
