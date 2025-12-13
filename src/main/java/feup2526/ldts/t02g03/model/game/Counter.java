package feup2526.ldts.t02g03.model.game;

public abstract class Counter {
    protected int count;

    public Counter() {
        this.count = 0;
    }

    public Counter(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count++;
    }

    public void increment(int i) {
        this.count += i;
    }

    public void decrement() {
        this.count--;
    }

    public void decrement(int i) {
        this.count -= i;
    }

    public int getCount() {
        return this.count;
    }
}
