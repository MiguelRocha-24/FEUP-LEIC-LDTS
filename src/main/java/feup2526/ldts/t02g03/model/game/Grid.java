package feup2526.ldts.t02g03.model.game;

public final class Grid {
    private final int w,h;

    public Grid(int w, int h) {
        if (w <= 0 || h <= 0)
            throw new IllegalArgumentException("Grid dimensions must be positive");
        this.w = w;
        this.h = h;
    }

    public int getW() {return this.w;}
    public int getH() {return this.h;}

    public boolean isInside(Position p) {
        return (p.getX() >= 0) && (p.getX() < this.w);
    }
}
