package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class SafeLane extends Lane {
    private List<Tree> trees;

    public SafeLane(int row,int width,boolean spawnTrees) {
        super(row);
        this.trees = new ArrayList<Tree>();
        if (spawnTrees) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < 0.25) {
                    this.addTree(new Tree(new Position(x, row)));
                }
            }
        }
    }

    public List<Tree> getTrees() {return trees;}

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
    @Override
    public void update(){
    }
}