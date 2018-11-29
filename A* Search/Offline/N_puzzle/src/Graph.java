import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

    private Board startboard;
    private Board goal;
    private int cutoff;
    private int boardSize;
    private PriorityQueue<Board> openlist;
    private List<Board> closelist;

    public Graph(Board board, int cutoff, int boardSize) {
        this.startboard = board;
        this.cutoff = cutoff;
        this.boardSize = boardSize;


        openlist = new PriorityQueue<>();
        closelist = new ArrayList<>();
    }

    public Board getStartboard() {
        return startboard;
    }

    private void Hamming(){
//        https://codereview.stackexchange.com/questions/19644/optimizations-to-8-puzzle
    }

    private void Manhattan(){

    }

    private void Lin_Conflict(){

    }

    private void getNeighbours(){



//        ArrayList<Board> tempneighbors = new ArrayList<Board>();
//
////        assignZeroLoc(); //determines both x and y loc's of the zero
//
//        for (int i = -1; i < 2; i++) { //creating all surrounding x coordinates
//            int p = zeroLocX + i; //current array being looked at
//            if (p < 0 || p > N - 1)
//                continue; //meaning these squares are out of bounds
//            for (int j = -1; j < 2; j++) {
//                int q = zeroLocY + j; //current index in current array
//                if (q < 0 || q > N - 1 || (p == zeroLocX && q == zeroLocY) || //if we are out of bounds or at the same square
//                        ((Math.abs(zeroLocX - p) + Math.abs(zeroLocY - q))) > 1) //or if delta x + delta y is greater than 1, aka at a diagonal space
//                    continue; //skip this iteration
//
//                int[][] temptiles = new int[boardSize][boardSize];
//
//                for (int m = 0; m < boardSize; m++)
//                    temptiles[m] = Arrays.copyOf(startboard.getBoardSize()[m][n], N); //copy the original board
//
//                int tempQ = temptiles[p][q]; //store the value of the value to swap
//                temptiles[p][q] = 0; //place the 0 in a valid location
//                temptiles[zeroLocX][zeroLocY] = tempQ; //place the stored value to swap where the 0 was
//                EightPuzzle neighbor = new EightPuzzle(temptiles, this,
//                        this.moves + 1); //create a new 8 puzzle
//                tempneighbors.add(neighbor); //add it to the arraylist
//                totalEnqueued++;
//
//            }
//
//        }
//
//        EightPuzzle[] neighbors = new EightPuzzle[tempneighbors.size()];
//
//        return tempneighbors.toArray(neighbors);

    }


}
