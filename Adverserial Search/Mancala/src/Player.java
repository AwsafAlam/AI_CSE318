import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Player {

    private Board myboard;
    private boolean isOpponent;
    private List<Integer> playerBin;
    private List<Integer> opponentBin;
    private int successor;
    private int heuristic;

    public int getMyStorage() {
        if(isOpponent)
            return myboard.getLowerBinStorage();
        else
            return myboard.getUpperBinStorage();
    }

    private int myStorage;

    public int getOpponentStorage() {
        if(isOpponent)
            return myboard.getUpperBinStorage();
        else
            return myboard.getLowerBinStorage();
    }

    private int opponentStorage;
    private int freeturn;
    private int captured_stones;

    public Player(Board myboard, boolean opponent, int heuristic) {
        this.successor = 0;
        this.myboard = myboard;
        this.isOpponent = opponent;
        this.heuristic = heuristic;
        if(isOpponent){
            playerBin = myboard.getUpperBin();
            opponentBin = myboard.getLowerBin();
            opponentStorage = myboard.getLowerBinStorage();
            myStorage = myboard.getUpperBinStorage();
        }
        else{
            playerBin = myboard.getLowerBin();
            opponentBin = myboard.getUpperBin();
            opponentStorage = myboard.getUpperBinStorage();
            myStorage = myboard.getLowerBinStorage();
        }

    }



    public void selectMove(int choice){
        if(choice == 1){
            // players selecting randomly
            freeturn = 0;captured_stones=0;
            Random random = new Random();
            while(true){
                boolean turn = makemove(random.nextInt(5));
                if(turn)
                    continue;
                else
                    break;
            }
        }
        else if(choice == 2){
            freeturn = 0;captured_stones=0;
            while(true){
                //System.out.println("...MINMAX called...");
                MIN_MAX(myboard,6,true,-9999999,9999999); //TODO: Pass in a new reassigned board, not original one.
                boolean turn = makemove(successor);
                if(turn)
                    continue;
                else
                    break;
            }

        }
        else{
            // Human input
            freeturn = 0;captured_stones=0;
            myboard.printBoard();
            Scanner sc = new Scanner(System.in);
            System.out.print("Make your move : ");
            while (makemove(sc.nextInt()));
        }
    }

    public boolean makemove(int pos) {
        //System.out.println("DEBUG: Makemove called... "+pos);

        if(playerBin.get(pos) == 0){
            System.out.println("Invalid move");
            return false;
        }
        if(isOpponent){
            myStorage = myboard.getUpperBinStorage();
            opponentStorage = myboard.getLowerBinStorage();
        }
        else{
            myStorage = myboard.getLowerBinStorage();
            opponentStorage = myboard.getUpperBinStorage();
        }

        int stones = playerBin.get(pos);
        playerBin.set(pos,0);
        int newPos = pos;
        List<Integer> curr_Bin = playerBin;
        for (int i = 0; i < stones; i++) {

            if(curr_Bin.equals(playerBin))
            {
                //System.out.println("DEBUG: playrBin "+i);
                if(isOpponent)
                {
                    newPos--;
                    if(newPos == -1){
                        newPos = 0;myStorage++;i++;
                        //i++;opponentStorage++;
                        curr_Bin = opponentBin;
                    }
                }
                else
                {
                    newPos++;
                    if(newPos == 6)
                    {
                        newPos = 5;myStorage++;i++;
                        curr_Bin = opponentBin;
                    }
                }
                if(curr_Bin.get(newPos) == 0 && i == stones -1){
                    //capture all stones from opponent
                    System.out.println("Captured stone ---------");
                    captured_stones += opponentBin.get(newPos) + 1;
                    myStorage += opponentBin.get(newPos) + 1;
                    opponentBin.set(newPos , 0);
                    //myboard.printBoard();
                    continue;
                }

            }
            else
            {
                //System.out.println("DEBUG: opponentBin "+i);
                if(isOpponent)
                {
                    newPos++;
                    if(newPos == 6)
                    {
                        newPos = 5;//skiping opponent storage
                        curr_Bin = playerBin;
                        //opponentStorage++;i++;
                    }
                }
                else
                {
                    newPos--;
                    if(newPos == -1){
                        newPos = 0;//skipping opponent storage
                        curr_Bin = playerBin;
                        //opponentStorage++;i++;
                    }
                }
            }

            if(i==stones)
            {
                freeturn++;
                System.out.println("Free Turn ------------------------");
                if(isOpponent){
                    myboard.setUpperBinStorage(myStorage);
                    myboard.setLowerBinStorage(opponentStorage);
                }
                else{
                    myboard.setLowerBinStorage(myStorage);
                    myboard.setUpperBinStorage(opponentStorage);
                }
                //myboard.printBoard();
                return true;
                //break;
            }

            int val = curr_Bin.get(newPos);
            curr_Bin.set(newPos,val+1);

        }
        if(isOpponent){
            myboard.setUpperBinStorage(myStorage);
            myboard.setLowerBinStorage(opponentStorage);
        }
        else{
            myboard.setLowerBinStorage(myStorage);
            myboard.setUpperBinStorage(opponentStorage);
        }
        //myboard.printBoard();

        return false;
    }

    public int MIN_MAX(Board tempBoard, int depth, boolean ismax, int alpha, int beta)
    {
        if(depth <= 0 || tempBoard.gameOver())
            return evaluate(tempBoard);

        Board backupBoard = new Board(tempBoard);

        int curr_value, best_value;

        boolean f = false;

        if(ismax)
        {
            best_value = -9999999;

            for(int i = 0 ; i < Board.getTotalBins(); i++)
            {
                //boolean turn = makemove(i);
                if(playerBin.get(i) != 0){

                    boolean turn = backupBoard.generateMove(i, isOpponent);

                    if(turn) //TODO: need to also assign the player here
                    {
                        curr_value = MIN_MAX(backupBoard,depth -1, true , alpha , beta);
                    }
                    else{
                        curr_value = MIN_MAX(backupBoard, depth - 1, false, alpha, beta);

                    }


                    if(curr_value > best_value) {
                        best_value = curr_value;
                        successor = i;
                    }
                    alpha = max(alpha, best_value);
                }

                if(beta <= alpha)
                {
                    f = true;
                    break;
                }
            }

            //set successor
//            if(depth == D)
//                __successor__ = successor;

        }
        else
        {
            best_value = 99999999;
            for(int i = 0; i < Board.getTotalBins(); i++)
            {
                if(opponentBin.get(i) != 0) {

                    boolean turn = backupBoard.generateMove(i, !isOpponent);// For Min, play as the opponent of oppenent ie plays as myself

                    if (turn) {
                        curr_value = MIN_MAX(backupBoard, depth - 1, false, alpha, beta);
                    } else {
                        curr_value = MIN_MAX(backupBoard, depth - 1, true, alpha, beta);

                    }

                    best_value = min(best_value, curr_value);
                    beta = min(beta, best_value);
                }
                if(beta <= alpha)
                {
                    break;
                }
            }
        }

        return best_value;
    }

    private int evaluate(Board tempBoard) {
        if(heuristic == 1){
            return Heuristic1(tempBoard);
        }
        else if(heuristic == 2){
            return Heuristic2(tempBoard);
        }
        else if(heuristic == 3){
            return Heuristic3(tempBoard);
        }
        else{
            return Heuristic4(tempBoard);
        }

    }

    private int Heuristic1(Board tempBoard){
        if(isOpponent)
            return tempBoard.getLowerBinStorage() - tempBoard.getUpperBinStorage();
        else
            return tempBoard.getUpperBinStorage() - tempBoard.getLowerBinStorage();
    }

    private int Heuristic2(Board tempBoard){
        int W1 = 30, W2 = 20;
        if(isOpponent)
            return W1*(tempBoard.getLowerBinStorage()-tempBoard.getUpperBinStorage())+ W2*(tempBoard.getLowerBinStones()-tempBoard.getUpperBinStones());
        else
            return W1*(tempBoard.getUpperBinStorage()-tempBoard.getLowerBinStorage()) + W2*(tempBoard.getUpperBinStones()-tempBoard.getLowerBinStones());

    }

    private int Heuristic3(Board tempBoard){
        int W1 = 30, W2 = 20 ,W3 = 40;
        if(isOpponent)
        {
            return W1*(tempBoard.getLowerBinStorage()-tempBoard.getUpperBinStorage())+
                    W2*(tempBoard.getLowerBinStones()-tempBoard.getUpperBinStones()) +
                    W3 * freeturn;
        }
        else
        {
            return W1*(tempBoard.getUpperBinStorage()-tempBoard.getLowerBinStorage())+
                    W2*(tempBoard.getUpperBinStones()-tempBoard.getLowerBinStones())+
                    W3 * freeturn;
        }

    }

    private int Heuristic4(Board tempBoard){
        int W1 = 30, W2 = 20 , W3 = 40, W4 = 45;
        if(isOpponent)
        {
            return W1*(tempBoard.getLowerBinStorage()-tempBoard.getUpperBinStorage())+
                    W2*(tempBoard.getLowerBinStones()-tempBoard.getUpperBinStones()) +
                    W3 * freeturn +
                    W4 * captured_stones;
        }
        else
        {
          return W1*(tempBoard.getUpperBinStorage()-tempBoard.getLowerBinStorage())+
                 W2*(tempBoard.getUpperBinStones()-tempBoard.getLowerBinStones())+
                 W3 * freeturn +
                  W4 * captured_stones;
        }

    }
}
