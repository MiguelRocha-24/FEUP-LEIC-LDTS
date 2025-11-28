package feup2526.ldts.t02g03.view;
import feup2526.ldts.t02g03.model.Player;

public class PlayerViewer implements Viewer<Player> {
    @Override
    public char getSymbol(Player element) {
        return 'P';
    }
}
