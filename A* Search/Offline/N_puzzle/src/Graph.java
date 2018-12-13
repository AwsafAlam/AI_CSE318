import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {

    private static final int Hamming_Distance = 1;
    private static final int Manhattan_Distance = 2;
    private static final int Linear_Conflict = 3;

    private Board startboard;
    private int cutoff;
    private int boardSize;
    private int heuristic;
    private PriorityQueue<Board> openlist;
    private HashMap<Board , Integer> openset;
    private HashSet<Board> closedset;
    private FileWriter fileWriter;
    private HashMap<Integer, Pair<Integer,Integer>> goalNode;

    Graph(Board board, int cutoff, int boardSize, int h) throws IOException {
        this.startboard = board;
        this.cutoff = cutoff;
        this.boardSize = boardSize;
        this.heuristic = h;

        goalNode = new HashMap<>();
        int k = 1;
        for(int i =0 ; i< boardSize ; i++){
            for(int j=0 ; j< boardSize ; j++)
            {
                if( k >= (boardSize*boardSize)){
                    goalNode.put(0 , new Pair<>(i,j));
                    break;
                }
                goalNode.put(k , new Pair<>(i,j));
                k++;
            }

        }

        openlist = new PriorityQueue<>(new BoardComparator());
        openset = new HashMap<>();
        closedset = new HashSet<>();
        fileWriter = new FileWriter(new File("output.txt"));
    }

    public Board getStartboard() {
        return startboard;
    }

    private int getHamming(int mat[][]){
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

    private int Lin_Conflict(int mat[][]) throws IOException {
        //Arrays.toString(mat);
        int lc = 0;
        int dist = 0;
        //Horizontal Conflict
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Pair pos  = goalNode.get(mat[i][j]);

                if (mat[i][j] != 0){ //Manhattan
                    dist += Math.abs((Integer) pos.getKey() - i) +Math.abs((Integer) pos.getValue() - j);
                }
                if((Integer) pos.getKey() != i || mat[i][j] == 0)
                    continue;

                for (int k = j+1; k < boardSize; k++) {
                    if(goalNode.get(mat[i][k]).getKey() != i)
                        continue;
//                    fileWriter.write(" fc: "+goalNode.get(mat[i][k]).getKey()+ "<" +goalNode.get(mat[i][j]).getKey()+"");
//                    if(mat[i][k] != 0 &&
//                    goalNode.get(mat[i][k]).getKey() < goalNode.get(mat[i][j]).getKey()){

                    if(mat[i][k] != 0 &&  mat[i][k] < mat[i][j]){
//                        fileWriter.write("LC: ("+mat[i][j]+"-"+mat[i][k]+") ");
                        lc++;
                    }
                }

            }
            //System.out.println("");
        }


        //Vertical Conflict
        for (int j = 0; j < boardSize; j++) {
            for (int i = 0; i < boardSize; i++) {
                Pair pos = goalNode.get(mat[i][j]);
                if((Integer) pos.getValue() != j || mat[i][j] == 0)
                    continue;

                for (int k = i+1; k < boardSize; k++) {
                    if(goalNode.get(mat[k][j]).getKey() != j)
                        continue;

                    if(mat[k][j] != 0 &&
                            mat[k][j] < mat[i][j]){
                        System.out.print("LC: ("+mat[i][j]+"-"+mat[k][j]+") ");
//                        fileWriter.write("LC: ("+mat[i][j]+"-"+mat[k][j]+") ");
                        lc++;
                    }
                }

            }
        }
        System.out.println("\nLC : "+lc);
//        fileWriter.write("\nLin_Conf : "+lc+"\n");
        lc = dist + 2* lc;
        return lc;
    }

    private void getNeighbours(Board s) throws IOException {

        int I_idx=-1, J_idx=-1;
        boolean f = false;

        for (int i =0 ; i< boardSize ; i++){ //finding blank index
            for (int j =0 ; j< boardSize ; j++) {

                if (s.getMatrix()[i][j] == 0) {
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

                if(( Math.abs(i) == Math.abs(j) ) || (I_idx +i >= boardSize) ||
                (J_idx +j >= boardSize) || (I_idx +i < 0) || (J_idx +j < 0)){
                   continue;
                }
                
                int tmp[][] = new int[boardSize][boardSize];
                for (int k =0 ; k< boardSize ; k++){
                    for (int l =0 ; l< boardSize ; l++){
                        tmp[k][l] = s.getMatrix()[k][l];
                    }
                }

//                System.out.println("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+")");
                fileWriter.write("("+I_idx+ ","+J_idx+") -> (" +(I_idx+i)+"," +(J_idx+j)+") = " +
                        s.getMatrix()[I_idx+i][J_idx+j]+ " moved\n");

                tmp[I_idx][J_idx] = tmp[I_idx+i][J_idx+j];
                tmp[I_idx+i][J_idx+j] = 0;
                Board b = null;

                if(heuristic == Hamming_Distance){
                    b = new Board(boardSize,tmp , s, s.getDistance()+1,getHamming(tmp));
                }
                else if(heuristic == Manhattan_Distance){
                    b = new Board(boardSize,tmp , s, s.getDistance()+1,getManhattan(tmp));
                }
                else if(heuristic == Linear_Conflict){
                    b = new Board(boardSize,tmp , s, s.getDistance()+1, Lin_Conflict(tmp));

                }
                fileWriter.write(b.toString());
                if(closedset.contains(b)){
                    continue;
                }
                else { //not in closed list

                    if(openset.containsValue(b)){
                        int f_score = openset.get(b);
                        if(f_score > (b.getHeuristic() + b.getDistance())){
                            openlist.remove(b);
                            openset.remove(b);
                            openset.put(b , (b.getDistance()+b.getHeuristic()));
                        }

                    }
                    else{
                        openlist.add(b);
                        openset.put(b , (b.getDistance()+b.getHeuristic()));
                    }


                }


                if(b.isGoal()){
                    System.out.println("GOAL reached");
//                    return;
                }
            }
        }

    }

    private int searchOpenList(Board b) {

        for (Board board:openlist) {
            if (b.equals(board)){
                if( (b.getHeuristic() + b.getDistance()) < (board.getDistance() + board.getHeuristic()) ){
                    return (b.getHeuristic() + b.getDistance());
                }
                else{
                    return -2;
                }
            }
        }
        return -1;

    }


    public int A_Star_Search(Board s) throws IOException {

        int expanded = 0;

        s.setParent(null);
        if(heuristic == Hamming_Distance){
            s.setHeuristic(getHamming(s.getMatrix()));
        }
        else if(heuristic == Manhattan_Distance){
            s.setHeuristic(getManhattan(s.getMatrix()));
        }
        else if(heuristic == Linear_Conflict){
            s.setHeuristic(Lin_Conflict(s.getMatrix()));
        }
        openlist.add(s);
        openset.put(s,(s.getDistance()+s.getHeuristic()));


        while(!openlist.isEmpty())
        {
            boolean flag = true;
            Board uncovered = openlist.peek();
            openlist.poll();
            fileWriter.write("Parent -->\n"+uncovered.toString()+"\n<----------->\n");

            if(uncovered.isGoal()){
                System.out.println("Goal reached ---------> ");
                fileWriter.write("\n------------Goal reached ----------------\n");
                fileWriter.write(uncovered.toString()+"\n");

                Board tmp = uncovered.getParent();
                System.out.println(uncovered.toString());

                while (tmp != null){
                    System.out.println(tmp.toString());
                    fileWriter.write(tmp.toString()+"\n");
                    tmp = tmp.getParent();
                }
                System.out.println("Expanded :"+closedset.size()+
                        "\nExplored :"+(closedset.size()+openlist.size()));
                fileWriter.close();
                return uncovered.getDistance()+uncovered.getHeuristic();
            }
            else{

                closedset.add(uncovered);
                getNeighbours(uncovered);
                expanded++;

                if(expanded == cutoff){
                    System.out.println("Cut-off : "+cutoff+"limit exceeded\n" +
                            "Nodes expanded: "+closedset.size() +
                            "Explored : "+(openlist.size()+closedset.size()));

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
