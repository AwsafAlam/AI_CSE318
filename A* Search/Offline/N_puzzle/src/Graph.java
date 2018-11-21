import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

    private Board startboard;
    private Board goal;
    private int cutoff;
    private int BoardSize;
    private PriorityQueue<Board> openlist;
    private List<Board> closelist;

    public Graph(Board board, int cutoff, int boardSize) {
        this.startboard = board;
        this.cutoff = cutoff;
        BoardSize = boardSize;


        openlist = new PriorityQueue<>();
        closelist = new ArrayList<>();
    }

    public Board getStartboard() {
        return startboard;
    }

    private void getNeighbours(){

    }


}
