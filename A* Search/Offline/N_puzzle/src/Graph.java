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
    //https://codereview.stackexchange.com/questions/19644/optimizations-to-8-puzzle

    }

    private void Manhattan(){

    }

    private void Lin_Conflict(){

    }

    private List<Board> getNeighbours(Board s){
        List<Board> neighbours = new ArrayList<>();

        int mat[][] = s.getMatrix();
        int I_idx=-1, J_idx=-1;

        for (int i =0 ; i< boardSize ; i++){
            for (int j =0 ; j< boardSize ; j++) {
                if (mat[i][j] == 0) {
                    System.out.println("Found blank. Swapping");
                    I_idx = i;
                    J_idx = j;
                    break;
                }
            }
        }

        for(int i = -1 ; i< 2; i++){
            for (int j = -1; j<2 ; j++){

                if( (I_idx == 0 && i == -1) || (I_idx == boardSize && i==1) ||
                (J_idx == 0 && j==-1) || (J_idx == boardSize && j==1)){

                    continue;
                }

//                int tmp[][] = Arrays.copyOf(mat , boardSize*boardSize);
                int tmp[][] = mat;
                int temp = mat[I_idx][J_idx];

                tmp[I_idx][J_idx] = tmp[I_idx+i][J_idx+j];
                tmp[I_idx+i][J_idx+j] = temp;
                neighbours.add(new Board(boardSize,tmp , s, s.getDistance()+1,0));
            }
        }

        return  neighbours;

    }

    private int bestFirstSearch(Board s, int heuristic){

        int expanded = 0;

        s.setParent(null);
        openlist.add(s);


        while(!openlist.isEmpty())
        {
            boolean flag = true;
            Board uncovered = openlist.peek();
            openlist.poll();

            if(uncovered.isGoal()){
//                myfile<<"\nBFS dist : "<<uncovered->getDistance()<<endl;
//                myfile<<"expanded: "<<expanded<<endl;
//                myfile<<"Path : ";
//
//                cout<<"\nBFS dist : "<<uncovered->getDistance()<<endl;
//                cout<<"expanded: "<<expanded<<endl;
//                cout<<"Path : ";

//                State * temp = uncovered->getParent();
//                while(temp != null){
//                    myfile<<" ("<<temp->getMissionary()<<","<<temp->getCannibal()<<","<<temp->getSide()<<")  -- ";
//                    cout<<" ("<<temp->getMissionary()<<","<<temp->getCannibal()<<","<<temp->getSide()<<")  -- ";
//                    temp = temp->getParent();
//                }
//
//                return uncovered->getDistance();
            }
            else{

                List<Board> nextBoards = getNeighbours(uncovered);
                // uncovered->printState();
                // printf("\n --> ");

//                myfile<<" ("<<uncovered->getMissionary()<<","<<uncovered->getCannibal()<<","<<uncovered->getSide()<<") Dist: "<<uncovered->getDistance();
//                myfile << "\n --> ";

                closelist.add(uncovered);  //color[source]= BLACK;
                expanded++;

                while(!nextBoards.isEmpty()){

                    Board visit = nextBoards.get(0); //grey node (bfs)
                    nextBoards.remove(0);
                    //visit->printState();
//                    myfile<<" ("<<visit->getMissionary()<<","<<visit->getCannibal()<<","<<visit->getSide()<<") ";

                    if(visit.isSolvable()){
                        // visit->setDistance(visit->getParent()->getDistance() + 1);

                        if(visit.isGoal()){
//                            myfile<<"\nBFS dist : "<<visit->getDistance()<<endl;
//                            myfile<<"expanded: "<<expanded<<endl;
//                            myfile<<"Path : ";
//
//                            cout<<"\nBFS dist : "<<visit->getDistance()<<endl;
//                            cout<<"expanded: "<<expanded<<endl;
//                            cout<<"Path : ";
//
//                            State * temp = visit->getParent();
//                            while(temp != NULL){
//                                myfile<<" ("<<temp->getMissionary()<<","<<temp->getCannibal()<<","<<temp->getSide()<<")  -- ";
//                                cout<<" ("<<temp->getMissionary()<<","<<temp->getCannibal()<<","<<temp->getSide()<<")  -- ";
//
//                                temp = temp->getParent();
//                            }
//
//                            return visit->getDistance();
                        }
                        else{
                            openlist.add(visit);

                        }
                    }

                }
                // printf("\n -------------------------\n");
//                myfile<<"\n -------------------------\n";
                if(expanded == cutoff){
                    System.out.println("Cut-off limit exceeded\nNodes expanded: "+cutoff);
//                    cout<<"Depth: "<<uncovered->getDistance()<<endl;
                    return -1;
                }

            }

        }

//        myfile<<"Solution not possible"<<endl;
        return -1;

    }


}
