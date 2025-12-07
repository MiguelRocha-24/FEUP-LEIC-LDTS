package feup2526.ldts.t02g03.model;

public class CoinCounter extends Counter {
    public CoinCounter() {
        super(null);
        this.count = 0;
    }

    @Override
    protected void save() {
        // Do nothing
    }

    @Override
    protected int load() {
        return 0;
    }
}
