package feup2526.ldts.t02g03.model;

public class RunScore extends Counter {
    public RunScore() {
        super("docs/counters/runScore.dat");
        this.count = 0;
        save();
    }
}
