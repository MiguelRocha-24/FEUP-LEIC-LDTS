package feup2526.ldts.t02g03.model;

import java.util.ArrayList;
import java.util.List;

public class River extends MovableLane{
    private List<Log> logs;
    public River(int row, Direction direction, int speed){
        super(row, direction, speed);
        this.logs = new ArrayList<Log>();
    }

    public List<Log> getLogs(){return logs;}

    public void addLog(Log l) {
        if (l == null) throw new IllegalArgumentException("Log required");
        if (l.getPosition().getY() != row) throw new IllegalArgumentException("Log must be on this lane");
        if (l.getDirection() != direction) throw new IllegalArgumentException("Log must have same direction as lane");
        // Maintain x-order
        int x = l.getPosition().getX();
        int idx = 0;
        while (idx < logs.size() && logs.get(idx).getPosition().getX() <= x) {idx++;}
        logs.add(idx, l);
    }
    public void removeLog(Log l){
        if (l == null) throw new IllegalArgumentException("Log required");
        if (!logs.contains(l)) throw new IllegalArgumentException("Log not in lane");
        logs.remove(l);}
}
