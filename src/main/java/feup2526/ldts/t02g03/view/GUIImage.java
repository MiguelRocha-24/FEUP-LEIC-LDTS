package feup2526.ldts.t02g03.view;

public interface GUIImage {
    void setPixel(int x, int y, String color);

    int getWidth();

    int getHeight();

    boolean hasTransparency();

    void setTransparency(boolean hasTransparency);
}
