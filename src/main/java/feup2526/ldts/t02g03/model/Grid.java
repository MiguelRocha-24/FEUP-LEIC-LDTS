package feup2526.ldts.t02g03.model;

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
        return (p.getX() >= 0) && (p.getX() < this.w) && (p.getY() >= 0) && (p.getY() < this.h);
    }

    public Position clamp(Position p) {
        int x = Math.max(0, Math.min(w - 1, p.getX()));
        int y = Math.max(0, Math.min(h - 1, p.getY()));
        return new Position(x, y);
    }
}
