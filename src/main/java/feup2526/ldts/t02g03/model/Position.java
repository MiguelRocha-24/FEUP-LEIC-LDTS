package feup2526.ldts.t02g03.model;

public final class Position {
    private double x,y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {return x;}
    public double getY() {return y;}

    public Position up(){return new Position(x,y-1.0);}
    public Position down(){return new Position(x,y+1.0);}
    public Position left(){return new Position(x-1.0,y);}
    public Position right(){return new Position(x+1.0,y);}
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
