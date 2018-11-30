import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {

    private Board startboard;
    private int goal[][];
    private int cutoff;
    private int boardSize;
    private PriorityQueue<Board> openlist;
    private List<Board> closelist;
    private FileWriter fileWriter;
    private HashMap<Integer, Pair<Integer,Integer>> goalNode;

    public Graph(Board board, int cutoff, int boardSize) throws IOException {
        this.startboard = board;
        this.cutoff = cutoff;
        this.boardSize = boardSize;

        goal = new int[boardSize][boardSize];
        goalNode = new HashMap<>();
        int k = 1;
        for(int i =0 ; i< boardSize ; i++){
            for(int j=0 ; j< boardSize ; j++)
            {
                if( k >= (boardSize*boardSize)){
                    goal[i][j] = 0;
                    goalNode.put(0 , new Pair<>(i,j));
                    break;
                }
                goal[i][j] = k;
                goalNode.put(k , new Pair<>(i,j));
                k++;
            }

        }

        openlist = new PriorityQueue<>(new BoardComparator());
        closelist = new ArrayList<>();
        fileWriter = new FileWriter(new File("output.txt"));
    }

    public Board getStartboard() {
        return startboard;
    }

    private int getHamming(int mat[][]){
    //https://codereview.stackexchange.com/questions/19644/optimizations-to-8-puzzle
        int dist = 0;
        int k = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if( k >= (boardSize*boardSize)-1)
                    break;

                if(mat[i][j] != k){
                    dist++;
                }
                k++;
            }
            if( k >= (boardSize*boardSize)-1)
                break;
        }

        return dist;
    }

    private int getManhattan(int mat[][]){
        int dist = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (mat[i][j] != 0){
                    Pair p  = goalNode.get(mat[i][j]);
                    dist += Math.abs((Integer) p.getKey() - i) +Math.abs((Integer) p.getValue() - j);
                }
            }
        }
        return dist;
    }

    private void Lin_Conflict(){

    }

    private void getNeighbours(Board s) throws IOException {

        int mat[][] = new int[boardSize][boardSize];
        for (int k =0 ; k< s.getMatrix().length ; k++){
            mat[k] = s.getMatrix()[k];
        }
        int I_idx=-1, J_idx=-1;
        boolean f = false;
        for (int i =0 ; i< boardSize ; i++){
            for (int j =0 ; j< boardSize ; j++) {

                if (mat[i][j] == 0) {
                //System.out.println("Found blank. Swapping " +i+" " +j+"->"+mat[i][j]);
                    I_idx = i;
                    J_idx = j;
                    f = true;
                    break;
                }
            }
            if(f)
                break;
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
                
                int tmp[][] = new int[boardSize][boardSize];
                for (int k =0 ; k< boardSize ; k++){
                    for (int l =0 ; l< boardSize ; l++){
                    tmp[k][l] = mat[k][l];
                    }
                }

//                System.out.println("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+")");
                fileWriter.write("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+") = "+mat[I_idx+i][J_idx+j]+ " moved\n");

                tmp[I_idx][J_idx] = tmp[I_idx+i][J_idx+j];
                tmp[I_idx+i][J_idx+j] = 0;
//                Board b = new Board(boardSize,tmp , s, s.getDistance()+1,getHamming(tmp));
                Board b = new Board(boardSize,tmp , s, s.getDistance()+1,getManhattan(tmp));

                if(!searchClosedList(b) && !searchOpenList(b)){
                    openlist.add(b);

                }

//                fileWriter.write(b.toString() +"\n\n----------------\n");
                if(b.isGoal()){
                    System.out.println("GOAL reached");
//                    return;
                }
            }
        }

    }

    private boolean searchOpenList(Board b) {

        for (Board board:openlist) {
            if (b.equals(board)){
                System.out.println("Found in open list");
                return true;
            }
        }
        return false;
    }

    private boolean searchClosedList(Board b) {

        for (Board board:closelist) {
            if (b.equals(board)){
                System.out.println("Found in closed list");
                return true;
            }
        }
        return false;
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
                Board tmp = uncovered.getParent();

                while (tmp != null){
                    System.out.println(tmp.toString());
                    tmp = tmp.getParent();
                }

                fileWriter.close();
                return uncovered.getDistance()+uncovered.getHeuristic();
            }
            else{

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
