package feup2526.ldts.t02g03.model.menu;

public class Skin {
    private String name;
    private int cost;

    public Skin(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {return name;}

    public int getCost() {return cost;}

    public void setName(String name) {this.name = name;}

    public void setCost(int cost) {this.cost = cost;}

    @Override
    public String toString() {return String.format("%s C:%d", name, cost);}
}