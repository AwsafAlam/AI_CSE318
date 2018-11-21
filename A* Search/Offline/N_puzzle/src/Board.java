public class Board {

    private int boardSize;
    private int matrix[][];
    private Board prev;
    private int movesNeeded;

    public Board(int boardSize, int[][] matrix, Board prev, int movesNeeded) {
        this.boardSize = boardSize;
        this.matrix = matrix;
        this.prev = prev;
        this.movesNeeded = movesNeeded;
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
        return tmp;
    }

    public boolean isGoal(){

        return true;
    }

    public boolean isSolvable(){
        return true;
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

    public Board getPrev() {
        return prev;
    }

    public void setPrev(Board prev) {
        this.prev = prev;
    }

    public int getMovesNeeded() {
        return movesNeeded;
    }

    public void setMovesNeeded(int movesNeeded) {
        this.movesNeeded = movesNeeded;
    }
}
