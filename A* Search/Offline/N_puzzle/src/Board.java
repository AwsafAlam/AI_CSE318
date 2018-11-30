public class Board {

    private int boardSize;
    private int matrix[][];
    private Board parent;
    private int heuristic;
    private int distance;

    public Board(int boardSize, int[][] mat, Board prev, int distance, int heuristic) {
        this.boardSize = boardSize;
        this.matrix = new int[boardSize][boardSize];
        for(int i =0 ; i< mat.length ; i++)
            this.matrix[i] = mat[i];

        this.parent = prev;
        this.distance = distance;
        this.heuristic = heuristic;
    }

    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i< boardSize; i++){
            for(int j =0 ; j< boardSize ; j++){
                tmp += matrix[i][j] + " ";
            }
            tmp += "\n";
        }
        tmp+= "Dist : "+distance+"\n";
//        System.out.println(tmp);
        return tmp;
    }

    public boolean isGoal(){
        int goal[][] = new int[boardSize][boardSize];
        int i = 1;
        for(int k =0 ; i< boardSize ; i++){
            for(int j=0 ; j< boardSize ; j++)
            {
                if( i == (boardSize*boardSize)-1)
                    break;

                goal[k][j] = i;
                i++;
            }
        }

        for(i =0 ; i< boardSize ; i++){
            for(int j=0 ; j< boardSize ; j++)
            {
                if(matrix[i][j] != goal[i][j])
                    return  false;
            }
        }
        return true;
    }

    public boolean isSolvable(){
        return true;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Board getParent() {
        return parent;
    }

    public void setParent(Board prev) {
        this.parent = prev;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
