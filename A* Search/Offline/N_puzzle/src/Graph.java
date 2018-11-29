import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private FileWriter fileWriter;


    public Graph(Board board, int cutoff, int boardSize) throws IOException {
        this.startboard = board;
        this.cutoff = cutoff;
        this.boardSize = boardSize;

        openlist = new PriorityQueue<>(new BoardComparator());
        closelist = new ArrayList<>();
        fileWriter = new FileWriter(new File("output.txt"));
    }

    public Board getStartboard() {
        return startboard;
    }

    private void Hamming(){
    //https://codereview.stackexchange.com/questions/19644/optimizations-to-8-puzzle

    }

    private void Manhattan(){

    }

    private void Lin_Conflict(){

    }

    private void getNeighbours(Board s) throws IOException {
//        List<Board> neighbours = new ArrayList<>();

        int mat[][] = s.getMatrix();
        int I_idx=-1, J_idx=-1;

        for (int i =0 ; i< boardSize ; i++){
            for (int j =0 ; j< boardSize ; j++) {
                if (mat[i][j] == 0) {
                    System.out.println("Found blank. Swapping " +i+" " +j+"->"+mat[i][j]);
                    I_idx = i;
                    J_idx = j;
                    break;
                }
            }
        }

        for(int i = -1 ; i< 2; i++){
            for (int j = -1; j<2 ; j++){

//                if( (I_idx == 0 && i == -1) || (I_idx == boardSize && i==1) ||
//                (J_idx == 0 && j==-1) || (J_idx == boardSize && j==1) ||
//                ( Math.abs(i) == Math.abs(j) ) || (I_idx +i >= boardSize) ||
//                (J_idx +j >= boardSize) || (I_idx +i < 0) || (J_idx +j < 0)){
                if(( Math.abs(i) == Math.abs(j) ) || (I_idx +i >= boardSize) ||
                (J_idx +j >= boardSize) || (I_idx +i < 0) || (J_idx +j < 0)){

                    continue;
                }

//                int tmp[][] = Arrays.copyOf(s.getMatrix() , boardSize*boardSize);
                int tmp[][] = new int[boardSize][boardSize];
                for (int k =0 ; k< mat.length ; k++){
                    tmp[k] = mat[k];
                }
//                System.arraycopy(s.getMatrix(), 0, tmp, 0, s.getMatrix().length);

                System.out.println("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+")");
                fileWriter.write("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+")\n");

                tmp[I_idx][J_idx] = tmp[I_idx+i][J_idx+j];
                tmp[I_idx+i][J_idx+j] = 0;
                Board b = new Board(boardSize,tmp , s, s.getDistance()+1,0);
                openlist.add(b);
                fileWriter.write(b.toString() +"\n\n----------------\n");
//                b.toString();
            }
        }

//        return  neighbours;

    }

    public int bestFirstSearch(Board s, int heuristic) throws IOException {

        int expanded = 0;

        s.setParent(null);
        openlist.add(s);


        while(!openlist.isEmpty())
        {
            boolean flag = true;
            Board uncovered = openlist.peek();
            openlist.poll();
            fileWriter.write("Parent -->\n"+uncovered.toString()+"\n<----------->\n");

            if(uncovered.isGoal()){
                System.out.println("Goal reached ---------> ");

            }
            else{

//                List<Board> nextBoards = getNeighbours(uncovered);
                getNeighbours(uncovered);
                closelist.add(uncovered);  //color[source]= BLACK;
                expanded++;

//                while(!nextBoards.isEmpty()){
//
//                    Board visit = nextBoards.get(0); //grey node (bfs)
//                    nextBoards.remove(0);
//
//                    //System.out.println(visit.toString()+"\n------------------");
//                    fileWriter.write(visit.toString()+"\n------------------\n");
//                    if(visit.isSolvable()){
//                        // visit->setDistance(visit->getParent()->getDistance() + 1);
//
//                        if(visit.isGoal()){
//                            System.out.println("Goal reached ->");
//                        }
//                        else{
//                            openlist.add(visit);
//
//                        }
//                    }
//
//                }
                // printf("\n -------------------------\n");
//                myfile<<"\n -------------------------\n";

                if(expanded == cutoff){
                    System.out.println("Cut-off limit exceeded\nNodes expanded: "+cutoff);

                    fileWriter.close();
                    return -1;
                }

            }

        }

        System.out.println("Solution not possible");
        fileWriter.close();
        return -1;

    }


}
