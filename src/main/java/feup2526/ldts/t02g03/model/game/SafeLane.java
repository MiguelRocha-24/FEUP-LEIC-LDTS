package feup2526.ldts.t02g03.model.game;

import java.util.ArrayList;
import java.util.List;

public class SafeLane extends Lane {
    private List<Tree> trees;
    private List<Coin> coins;

    public SafeLane(int row,int width,boolean spawnTrees) {
        super(row);
        this.trees = new ArrayList<Tree>();
        this.coins = new ArrayList<Coin>();
        if (spawnTrees) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < 0.25) {
                    this.addTree(new Tree(new Position(x, row)));
                }
            }
            if (Math.random() < 0.25) {
                List<Integer> emptySpots = new ArrayList<>();
                for (int x = 0; x < width; x++) {
                    boolean occupied = false;
                    for (Tree t : trees) {
                        if (t.getPosition().getX() == x) {
                            occupied = true;
                            break;
                        }
                    }
                    if (!occupied) {
                        emptySpots.add(x);
                    }
                }

                if (!emptySpots.isEmpty()) {
                    int x = emptySpots.get((int) (Math.random() * emptySpots.size()));
                    this.addCoin(new Coin(new Position(x, row)));
                }
            }
        }
    }

    public List<Tree> getTrees() {return trees;}
    public List<Coin> getCoins() {return coins;}

    public void addTree(Tree t) {
        if (t == null) throw new IllegalArgumentException("Tree required");
        if (t.getPosition().getY() != row) throw new IllegalArgumentException("Tree must be on this lane");

        // Maintain x-order
        double x = t.getPosition().getX();
        int idx = 0;
        while (idx < trees.size() && trees.get(idx).getPosition().getX() <= x) {
            idx++;
        }
        trees.add(idx, t);
    }

    public void addCoin(Coin c) {
        if (c == null) throw new IllegalArgumentException("Coin required");
        if (c.getPosition().getY() != row) throw new IllegalArgumentException("Coin must be on this lane");
        coins.add(c);
    }
    @Override
    public void update(){
    }
}